package fr.itpasta.chadbot;

import fr.itpasta.chadbot.chadbot.ChadBot;
import fr.itpasta.chadbot.jdm_api.JDMAPI;
import fr.itpasta.chadbot.jdm_api.RelationsRequest;
import fr.itpasta.chadbot.save.Save;

import java.io.IOException;

public class Main {
    public static Save save;
    public static JDMAPI api;

    public static void main(String[] args) throws IOException {
        save = new Save();
        api = new JDMAPI();
        ChadBot discordBot = new ChadBot(args[0]);
        /*while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RelationsRequest.ask("film");
                }
            }).start();
        }*/
    }
}