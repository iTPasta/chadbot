package fr.itpasta.chadbot.generic;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordBotListener extends ListenerAdapter {
    private String commandPrefix;
    private Command[] commands;
    protected DiscordBot bot;

    public DiscordBotListener(String commandPrefix, Command[] commands, DiscordBot bot) {
        this.commandPrefix = commandPrefix;
        this.commands = commands;
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        if (message.length() >= commandPrefix.length()) {
            for (Command command : commands) {
                command.onCommand(message);
            }
        }
    }
}
