package fr.itpasta.chadbot.save;

import fr.itpasta.chadbot.generic.ObjectSaveFile;
import fr.itpasta.chadbot.generic.SaveDirectory;
import fr.itpasta.chadbot.jdm_api.RelationType;

import java.io.IOException;
import java.util.*;

public class ResponsesFile extends ObjectSaveFile {
    public ResponsesFile(SaveDirectory saveDirectory, String name) throws IOException {
        super(saveDirectory, name);
        if (exists() && isEmpty()) saveObject(new HashMap<RelationType, List<String>>());
    }

    public void addResponse(RelationType relationType, String response) throws IOException {
        Map<RelationType, List<String>> map = readAllResponses();
        if (map.containsKey(relationType)) {
            List<String> responses = map.get(relationType);
            if (!responses.contains(response)) responses.add(response);
            map.put(relationType, responses);
        } else {
            map.put(relationType, Collections.singletonList(response));
        }
        super.saveObject(map, true);
    }

    public Map<RelationType, List<String>> readAllResponses() {
        return (Map<RelationType, List<String>>) super.readObjects().get(0);
    }
}
