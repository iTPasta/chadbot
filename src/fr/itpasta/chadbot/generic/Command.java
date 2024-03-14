package fr.itpasta.chadbot.generic;

public abstract class Command {
    private String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void onCommand(String message);

    public String getName() {
        return name;
    }
}
