package fr.itpasta.chadbot.generic;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandEvent extends MessageReceivedEvent {
    private String name;
    private String[] args;
    private String text;

    public CommandEvent(JDA api, long responseNumber, Message message) {
        super(api, responseNumber, message);
        this.text = message.getContentDisplay();
        this.args = text.split(" ");
        this.name = this.args[0];
    }

    public String getName() {
        return name;
    }

    public String[] getArgs() {
        return args;
    }

    public String getText() {
        return text;
    }
}
