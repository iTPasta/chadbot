package fr.itpasta.chadbot.generic;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class DiscordMessage {
    private User author;
    private MessageChannelUnion channel;
    private Message message;
    private String stringMessage;

    public DiscordMessage(User author, MessageChannelUnion channel, Message message) {
        this.author = author;
        this.channel = channel;
        this.message = message;
        this.stringMessage = message.getContentDisplay();
    }

    public User getAuthor() {
        return author;
    }

    public MessageChannelUnion getChannel() {
        return channel;
    }

    public Message getMessage() {
        return message;
    }

    public String getStringMessage() {
        return stringMessage;
    }
}
