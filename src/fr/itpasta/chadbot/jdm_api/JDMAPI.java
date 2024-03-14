package fr.itpasta.chadbot.jdm_api;

import fr.itpasta.chadbot.generic.StringUtils;
import fr.itpasta.chadbot.generic._2Uplet;

import java.io.IOException;
import java.util.HashMap;

import static fr.itpasta.chadbot.Main.save;

public class JDMAPI {
    public JDMAPI() {
    }

    public _2Uplet<LexicalGraph, Integer> getLexicalGraph(String word) throws IOException {
        LexicalGraph lexicalGraph = save.getLexicalGraph(word);
        if (lexicalGraph != null) {
            System.out.println("Lecture du mot : " + word);
            return new _2Uplet<>(lexicalGraph, 0);
        }

        String html = StringUtils.getHTML("https://www.jeuxdemots.org/rezo-dump.php?gotermsubmit=Chercher&gotermrel="
                + StringUtils.encodeValue(word) + "&rel=");

        String nodesStr = StringUtils.cutDelimitedPart(html, "e;eid;'name';type;w;'formated name' \n\n", "\n\n");
        String outgoingRelationsStr = StringUtils.cutDelimitedPart(html, " relations sortantes \n\n", "\n\n");
        String incomingRelationsStr = StringUtils.cutDelimitedPart(html, " relations entrantes \n\n", "\n</CODE>");

        String[][] nodesStrTab = StringUtils.createTabFromString(nodesStr, '\n', ';');
        Node[] nodesArray = new Node[nodesStrTab.length];
        HashMap<Integer, Node> nodesMap = new HashMap<>();
        for (int i = 0; i < nodesStrTab.length; i++) {
            if (nodesStrTab[i].length == 5 || nodesStrTab[i].length == 6) {
                Node node = new Node(nodesStrTab[i]);
                nodesArray[i] = node;
                nodesMap.put(node.getEid(), node);
            }
        }

        String[][] outgoingRelationsStrTab = StringUtils.createTabFromString(outgoingRelationsStr, '\n', ';');
        Relation[] outgoingRelationsArray = new Relation[outgoingRelationsStrTab.length];
        for (int i = 0; i < outgoingRelationsStrTab.length; i++) {
            if (outgoingRelationsStrTab[i].length == 6) {
                try {
                    outgoingRelationsArray[i] = new Relation(outgoingRelationsStrTab[i], nodesMap);
                } catch (NumberFormatException e) {
                    outgoingRelationsArray[i] = null;
                }
            }
        }

        String[][] incomingRelationsStrTab = StringUtils.createTabFromString(incomingRelationsStr, '\n', ';');
        Relation[] incomingRelationsArray = new Relation[incomingRelationsStrTab.length];
        for (int i = 0; i < incomingRelationsStrTab.length; i++) {
            if (incomingRelationsStrTab.length == 6) {
                try {
                    incomingRelationsArray[i] = new Relation(incomingRelationsStrTab[i], nodesMap);
                } catch (NumberFormatException e) {
                    incomingRelationsArray[i] = null;
                }
            }
        }

        lexicalGraph = new LexicalGraph(word, nodesArray, incomingRelationsArray, outgoingRelationsArray);
        save.createSaveFile(lexicalGraph);

        System.out.println("Demande effectuée au serveur : " + word);
        return new _2Uplet<>(lexicalGraph, 1);
    }

    public boolean relationExists(String word1, String word2, RelationType relationType) throws IOException {
        LexicalGraph lexicalGraph = getLexicalGraph(word1).getO1();
        return lexicalGraph.relationExists(relationType, word2);
    }

    public boolean relationExists(String word1, String word2, String relationTypeName) throws IOException {
        return relationExists(word1, word2, RelationType.getWithName(relationTypeName));
    }
}
