package fr.itpasta.chadbot.save;

import fr.itpasta.chadbot.generic.ObjectSaveFile;
import fr.itpasta.chadbot.generic.SaveDirectory;
import fr.itpasta.chadbot.jdm_api.RelationType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PatternsFile extends ObjectSaveFile {
    public PatternsFile(SaveDirectory saveDirectory) throws IOException {
        super(saveDirectory, "patterns.txt");
        if (exists() && isEmpty()) saveObject(new HashMap<Pattern, RelationType>());
    }

    public void addPattern(Pattern pattern, RelationType relationType) throws IOException {
        Map<Pattern, RelationType> map = readAllPatterns();
        map.put(pattern, relationType);
        super.saveObject(map, true);
    }

    public Map<Pattern, RelationType> readAllPatterns() {
        return (Map<Pattern, RelationType>) super.readObjects().get(0);
    }
}
