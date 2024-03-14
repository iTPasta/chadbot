package fr.itpasta.chadbot.generic;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SaveDirectory extends File {

    public SaveDirectory(@NotNull String directoryName) {
        super(System.getProperty("user.home") + "/" + directoryName);
    }

    public boolean checkExistence() {
        return exists();
    }

    public boolean create() {
        if (!exists()){
            return mkdir();
        }
        return false;
    }

    public File[] getFiles() {
        return listFiles();
    }

    public boolean containsFile(String name) {
        return new File(getPath() + "/" + name).exists();
    }
}
