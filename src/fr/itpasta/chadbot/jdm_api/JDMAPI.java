package fr.itpasta.chadbot.jdm_api;

import static fr.itpasta.chadbot.Main.save;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fr.itpasta.chadbot.generic.StringUtils;
import fr.itpasta.chadbot.generic._2Uplet;

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
        List<Relation> outgoingRelationsArray = new ArrayList<>();
        for (int i = 0; i < outgoingRelationsStrTab.length; i++) {
            if (6 <= outgoingRelationsStrTab[i].length) {
                try {
                	outgoingRelationsArray.add(new Relation(outgoingRelationsStrTab[i], nodesMap));
                } catch (NumberFormatException e) {
                	e.printStackTrace();
                }
            }
        }

        String[][] incomingRelationsStrTab = StringUtils.createTabFromString(incomingRelationsStr, '\n', ';');
        List<Relation> incomingRelationsArray = new ArrayList<>();
        for (int i = 0; i < incomingRelationsStrTab.length; i++) {
            if (6 <= incomingRelationsStrTab[i].length) {
                try {
                    incomingRelationsArray.add(new Relation(incomingRelationsStrTab[i], nodesMap));
                } catch (NumberFormatException e) {
                	e.printStackTrace();
                }
            }
        }

        lexicalGraph = new LexicalGraph(
	        		word,
	        		nodesArray,
	        		incomingRelationsArray.toArray(new Relation[0]),
	        		outgoingRelationsArray.toArray(new Relation[0])
        		);
        
        try {
        	save.createSaveFile(lexicalGraph);
        } catch (IOException e) {
        	System.out.println("Erreur lors de la lecture du fichier '" + word + ".dat', probablement un problème d'accent.");
        }

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
