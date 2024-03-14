package fr.itpasta.chadbot.chadbot.states;

import java.util.regex.Pattern;

public class WaitingForRelationType extends ChadBotState {
    private Pattern pattern;

    public WaitingForRelationType(Pattern pattern) {
        super("WAITING_FOR_RELATIONTYPE");
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
