package fr.itpasta.chadbot.generic;

import fr.itpasta.chadbot.jdm_api.LexicalGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectSaveFile extends File {
    private SaveDirectory saveDirectory;

    public ObjectSaveFile(SaveDirectory saveDirectory, String fileName) {
        super(saveDirectory.getPath() + "/" + fileName);
        this.saveDirectory = saveDirectory;
    }

    public boolean create() throws IOException {
        if (!exists()) {
            return createNewFile();
        } else {
            throw new IOException("Le fichier existe déjà.");
        }
    }

    public void saveObject(Object object, boolean eraseData) throws IOException {
        if (!exists()) {
            throw new IOException("Le fichier n'existe pas.");
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(this, !eraseData);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
        }
    }

    public void saveObject(Object object) throws IOException {
        saveObject(object, false);
    }

    public List<Object> readObjects() {
        List<Object> objects = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(this);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object;
            while ((object = objectInputStream.readObject()) != null) {
                objects.add(object);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors de la lecture des objets : " + e.getMessage());
        }
        return objects;
    }

    public boolean isEmpty() {
        return readObjects().isEmpty();
    }

    public static List<Object> readObjects(String fileName) {
        List<Object> objects = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object;
            while ((object = objectInputStream.readObject()) != null) {
                objects.add(object);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors de la lecture des objets : " + e.getMessage());
        }
        return objects;
    }

    public void clearFile() throws IOException {
        if (!exists()) {
            throw new IOException("Le fichier n'existe pas.");
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(this)) {
            fileOutputStream.write(new byte[0]);
        }
    }

    public void deleteFile() throws IOException {
        if (!exists()) {
            throw new IOException("Le fichier n'existe pas.");
        }

        if (!delete()) {
            throw new IOException("Erreur lors de la suppression du fichier.");
        }
    }
}
