package fr.itpasta.chadbot.chadbot.states;

public abstract class ChadBotState {
    private final String stateName;

    public ChadBotState(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}
