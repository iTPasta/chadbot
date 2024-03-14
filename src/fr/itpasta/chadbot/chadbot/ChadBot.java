package fr.itpasta.chadbot.chadbot;

import fr.itpasta.chadbot.generic.Command;
import fr.itpasta.chadbot.generic.DiscordBot;

public class ChadBot extends DiscordBot {
    public ChadBot(String token) {
        super(token, "chadbot");
        Command[] commands = {};
        super.jda.addEventListener(new ChadBotListener(this, commands));
    }
}
