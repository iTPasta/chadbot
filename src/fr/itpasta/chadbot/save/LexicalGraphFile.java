package fr.itpasta.chadbot.save;

import fr.itpasta.chadbot.generic.ObjectSaveFile;
import fr.itpasta.chadbot.generic.SaveDirectory;
import fr.itpasta.chadbot.jdm_api.LexicalGraph;

import java.io.IOException;

public class LexicalGraphFile extends ObjectSaveFile {
    private final LexicalGraph lexicalGraph;

    public LexicalGraphFile(SaveDirectory saveDirectory, LexicalGraph lexicalGraph) {
        super(saveDirectory, lexicalGraph.getMainWord() + ".dat");
        this.lexicalGraph = lexicalGraph;
    }

    public boolean create() throws IOException {
        boolean success = super.create();
        if (success) {
            saveObject(lexicalGraph);
        }
        return success;
    }
}
