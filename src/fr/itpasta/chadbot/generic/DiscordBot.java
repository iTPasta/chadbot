package fr.itpasta.chadbot.generic;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

public abstract class DiscordBot {
    protected JDA jda = null;
    protected String defaultChannelName;

    public DiscordBot(String token, String defaultChannelName) {
        this.defaultChannelName = defaultChannelName;

        EnumSet<GatewayIntent> intents = EnumSet.of(
                // Enables MessageReceivedEvent for guild (also known as servers)
                GatewayIntent.GUILD_MESSAGES,
                // Enables the event for private channels (also known as direct messages)
                GatewayIntent.DIRECT_MESSAGES,
                // Enables access to message.getContentRaw()
                GatewayIntent.MESSAGE_CONTENT,
                // Enables MessageReactionAddEvent for guild
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                // Enables MessageReactionAddEvent for private channels
                GatewayIntent.DIRECT_MESSAGE_REACTIONS
        );

        // To start the bot, you have to use the JDABuilder.

        // You can choose one of the factory methods to build your bot:
        // - createLight(...)
        // - createDefault(...)
        // - create(...)
        // Each of these factory methods use different defaults, you can check the documentation for more details.

        while(jda == null) {
            try {
                jda = JDABuilder.createLight(token, intents)
                        .setActivity(Activity.playing("Dofus"))
                        .build();

                jda.getRestPing().queue(ping ->
                        // shows ping in milliseconds
                        System.out.println("Logged in with ping: " + ping)
                );
                jda.awaitReady();

                System.out.println("Guilds: " + jda.getGuildCache().size());
            } catch (InterruptedException e) {
                // Thrown if the awaitReady() call is interrupted
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(MessageChannelUnion channel, String message) {
        channel.sendMessage(message).complete();
    }

    public void sendMessage(Guild guild, String message) {
        for (TextChannel channels : guild.getTextChannelsByName(defaultChannelName, false)) {
            channels.sendMessage(message).complete();
        }
    }

    public boolean isDefaultChannel(Channel channel) {
        return channel.getName().equals(defaultChannelName);
    }

    public String getDefaultChannelName() {
        return defaultChannelName;
    }
}
