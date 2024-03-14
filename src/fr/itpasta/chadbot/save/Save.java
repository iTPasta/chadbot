package fr.itpasta.chadbot.save;

import fr.itpasta.chadbot.generic.SaveDirectory;
import fr.itpasta.chadbot.jdm_api.LexicalGraph;
import fr.itpasta.chadbot.jdm_api.RelationType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.regex.Pattern;

public class Save {
    private SaveDirectory saveDirectory;
    private PatternsFile patternsFile;
    private Map<Pattern, RelationType> patterns;
    private ResponsesFile positivesResponsesFile;
    private Map<RelationType, List<String>> positivesResponses;
    private ResponsesFile negativesResponsesFile;
    private Map<RelationType, List<String>> negativesResponses;
    private List<File> saveFiles;

    public Save() throws IOException {
        this.saveDirectory = new SaveDirectory(".chadbot");
        if (!this.saveDirectory.checkExistence()) {
            this.saveDirectory.create();
        }
        this.saveFiles = new ArrayList<File>();
        saveFiles.addAll(Arrays.asList(saveDirectory.getFiles()));

        this.patternsFile = new PatternsFile(this.saveDirectory);
        if (!this.patternsFile.exists()) {
            this.patternsFile.create();
            this.patternsFile.saveObject(new HashMap<Pattern, RelationType>());
        }
        this.patterns = patternsFile.readAllPatterns();

        this.positivesResponsesFile = new ResponsesFile(this.saveDirectory, "positives_responses.txt");
        if (!this.positivesResponsesFile.exists()) {
            this.positivesResponsesFile.create();
            this.positivesResponsesFile.saveObject(new HashMap<RelationType, List<String>>());
        }
        this.positivesResponses = positivesResponsesFile.readAllResponses();

        this.negativesResponsesFile = new ResponsesFile(this.saveDirectory, "negatives_responses.txt");
        if (!this.negativesResponsesFile.exists()) {
            this.negativesResponsesFile.create();
            this.negativesResponsesFile.saveObject(new HashMap<RelationType, List<String>>());
        }
        this.negativesResponses = negativesResponsesFile.readAllResponses();
    }

    public boolean createSaveFile(LexicalGraph lexicalGraph) throws IOException {
        LexicalGraphFile lexicalGraphSaveFile = new LexicalGraphFile(saveDirectory, lexicalGraph);
        boolean success = lexicalGraphSaveFile.create();
        if (success) {
            saveFiles.add(lexicalGraphSaveFile);
        }
        return success;
    }

    public void addPattern(Pattern pattern, RelationType relationType) throws IOException {
        patternsFile.addPattern(pattern, relationType);
        patterns.put(pattern, relationType);
    }

    public Map<Pattern, RelationType> getPatterns() {
        return patterns;
    }

    public void addPositiveResponse(RelationType relationType, String response) throws IOException {
        positivesResponsesFile.addResponse(relationType, response);
        if (positivesResponses.containsKey(relationType)) {
            List<String> responsesStr = positivesResponses.get(relationType);
            if (!responsesStr.contains(response)) responsesStr.add(response);
            positivesResponses.put(relationType, responsesStr);
        } else {
            positivesResponses.put(relationType, Collections.singletonList(response));
        }
    }

    public List<String> getPositivesResponses(RelationType relationType) {
        if (!positivesResponses.containsKey(relationType)) {
            positivesResponses.put(relationType, new ArrayList<String>());
        }
        return positivesResponses.get(relationType);
    }

    public Map<RelationType, List<String>> getAllPositivesResponses() {
        return positivesResponses;
    }

    public void addNegativeResponse(RelationType relationType, String response) throws IOException {
        negativesResponsesFile.addResponse(relationType, response);
        if (negativesResponses.containsKey(relationType)) {
            List<String> responsesStr = negativesResponses.get(relationType);
            if (!responsesStr.contains(response)) responsesStr.add(response);
            negativesResponses.put(relationType, responsesStr);
        } else {
            negativesResponses.put(relationType, Collections.singletonList(response));
        }
    }

    public List<String> getNegativesResponses(RelationType relationType) {
        if (!negativesResponses.containsKey(relationType)) {
            negativesResponses.put(relationType, new ArrayList<String>());
        }
        return negativesResponses.get(relationType);
    }

    public Map<RelationType, List<String>> getAllNegativesResponses() {
        return negativesResponses;
    }

    private boolean containsFile(String fileName) {
        for (File file : saveDirectory.getFiles()) {
            if (file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsWord(String word) {
        return containsFile(word + ".dat");
    }

    private File getFile(String fileName) {
        for (File file : saveDirectory.getFiles()) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    public LexicalGraph getLexicalGraph(String word) {
        LexicalGraph lexicalGraph = null;
        File file = getFile(word + ".dat");

        if (file != null && file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                Object object = objectInputStream.readObject();

                if (object instanceof LexicalGraph) {
                    lexicalGraph = (LexicalGraph) object;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Erreur de lecture du fichier : " + e.getMessage());
            }

            return lexicalGraph;
        } else {
            return null;
        }
    }
}
