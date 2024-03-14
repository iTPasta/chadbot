package fr.itpasta.chadbot.chadbot;

import fr.itpasta.chadbot.chadbot.states.Active;
import fr.itpasta.chadbot.chadbot.states.ChadBotState;
import fr.itpasta.chadbot.chadbot.states.WaitingForRelationType;
import fr.itpasta.chadbot.chadbot.states.WaitingForResponse;
import fr.itpasta.chadbot.generic.Command;
import fr.itpasta.chadbot.generic.DiscordBot;
import fr.itpasta.chadbot.generic.DiscordBotListener;
import fr.itpasta.chadbot.jdm_api.RelationType;
import fr.itpasta.chadbot.jdm_api.ResponseType;
import fr.itpasta.chadbot.jdm_api.graph_browsing.BrowsingMethod;
import fr.itpasta.chadbot.jdm_api.graph_browsing.Proof;
import fr.itpasta.chadbot.jdm_api.graph_browsing.Validity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.itpasta.chadbot.Main.save;

public class ChadBotListener extends DiscordBotListener {
    private ChadBotState state;
    private static final String commandPrefix = "//";

    public ChadBotListener(DiscordBot bot, Command[] commands) {
        super(commandPrefix, commands, bot);
        this.state = new Active();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        User author = event.getAuthor();
        if (author.isBot()) return;
        MessageChannelUnion channel = event.getChannel();
        if (channel.getType() != ChannelType.TEXT || !bot.isDefaultChannel(channel)) return;


        Message message = event.getMessage();
        String messageStr = message.getContentDisplay();
        String word1 = null;
        String word2 = null;
        RelationType relationType = null;

        switch (state.getStateName()) {
            case "ACTIVE":
                Map<Pattern, RelationType> patterns = save.getPatterns();
                for (Map.Entry<Pattern, RelationType> entry : patterns.entrySet()) {
                    Matcher matcher = entry.getKey().matcher(messageStr);
                    if (matcher.matches()) {
                        word1 = matcher.group("word1");
                        word2 = matcher.group("word2");
                        relationType = entry.getValue();
                        break;
                    }
                }

                if (word1 == null || word2 == null || relationType == null) {
                    if (!messageStr.contains("$a") || !messageStr.contains("$z")) {
                        bot.sendMessage(channel, "Votre enregistrement manque de formattage ($a et $z).");
                    } else {
                        Pattern pattern = Pattern.compile(
                                messageStr
                                        .replace("?", "\\?")
                                        .replace("$a", "(?<word1>.*)")
                                        .replace("$z", "(?<word2>.*)"),
                                Pattern.CASE_INSENSITIVE);
                        state = new WaitingForRelationType(pattern);
                        bot.sendMessage(channel, "Attente du nom de la relation.");
                    }
                } else {
                    Proof proof = null;
                    try {
                        proof = BrowsingMethod.applyAll(word1, word2, relationType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String messageToSend;
                    if (proof.getValidity() == Validity.VALID) {
                        bot.sendMessage(channel, "Oui car " + proof);
                        /*if (!save.getPositivesResponses(relationType).isEmpty()) {
                            messageToSend = "Oui car " + proof;
                            bot.sendMessage(channel, messageToSend);
                        } else {
                            state = new WaitingForResponse(relationType, ResponseType.POSITIVE); // PEUT ETRE PAS BON
                            bot.sendMessage(channel, "Attente d'un format de preuve pour la relation citée.");
                        }*/
                    } else if (proof.getValidity() == Validity.INVALID) {
                        bot.sendMessage(channel, "Non car " + proof);
                        /*System.out.println(save.getNegativesResponses(relationType));
                        if (!save.getNegativesResponses(relationType).isEmpty()) {
                            bot.sendMessage(channel, save.getNegativesResponses(relationType).get(0).replace("$a", word1).replace("$z", word2));
                        } else {
                            state = new WaitingForResponse(relationType, ResponseType.NEGATIVE);
                            bot.sendMessage(channel, "Attente d'un format de réponse négative pour la relation citée.");
                        }*/
                    } else if (proof.getValidity() == Validity.UNKNOWN) {
                        bot.sendMessage(channel, "La base de donnée ne permet malheuresement pas de répondre à cette question.");
                    }
                }
                break;
            case "WAITING_FOR_RELATIONTYPE":
                if (messageStr.equalsIgnoreCase("cancel")) {
                    state = new Active();
                    bot.sendMessage(channel, "Enregistrement annulé.");
                } else {
                    try {
                        relationType = RelationType.getWithName(messageStr);
                        save.addPattern(((WaitingForRelationType) state).getPattern(), relationType);
                        if (relationType != null) {
                            bot.sendMessage(channel, "Relation enregistrée.");
                        } else {
                            bot.sendMessage(channel, "Relation inexistante, enregistrement annulé.");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    state = new Active();
                }
                break;
            case "WAITING_FOR_RESPONSE":
                if (messageStr.equalsIgnoreCase("cancel")) {
                    state = new Active();
                    bot.sendMessage(channel, "Enregistrement annulé.");
                } else {
                    try {
                        WaitingForResponse currentState = ((WaitingForResponse) state);
                        if (currentState.getResponseType() == ResponseType.POSITIVE) {
                            save.addPositiveResponse(currentState.getRelationType(), messageStr);
                            bot.sendMessage(channel, "Format de preuve enregistré.");
                        } else {
                            save.addNegativeResponse(currentState.getRelationType(), messageStr);
                            bot.sendMessage(channel, "Format de réponse négative enregistré.");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    state = new Active();
                }
                break;
            default:
                break;
        }


        /*
        String[] splitMessage = message.getContentDisplay().split(" ");
        try {
            RelationType relationType = null;
            int relationIndex = 1;
            while (relationIndex + 1 < splitMessage.length
                    && (relationType = RelationType.getWithName(splitMessage[relationIndex])) == null) {
                relationIndex++;
            }
            if (relationType != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < relationIndex; i++) {
                    sb.append(splitMessage[i]).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1);

                String word1 = sb.toString();
                sb = new StringBuilder();
                for (int i = relationIndex + 1; i < splitMessage.length; i++) {
                    sb.append(splitMessage[i]).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1);
                String word2 = sb.toString();

                channel.asTextChannel().sendMessage(api.relationExists(word1, word2, relationType) ? "Oui." : "Non.").complete();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
    }


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event)
    {
        /*if (event.getEmoji().equals(HEART)) {
            System.out.println("A user loved a message!");
        }*/
    }
}
