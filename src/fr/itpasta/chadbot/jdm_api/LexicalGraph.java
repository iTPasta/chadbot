package fr.itpasta.chadbot.jdm_api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalGraph implements Serializable {

    private String mainWord;
    private Node[] nodes;
    private Relation[] incomingRelations;
    private Relation[] outgoingRelations;

    public LexicalGraph(String mainWord, Node[] nodes, Relation[] incomingRelations, Relation[] outgoingRelations) {
        this.mainWord = mainWord;
        this.nodes = nodes;
        this.incomingRelations = incomingRelations;
        this.outgoingRelations = outgoingRelations;
    }

    public String getMainWord() {
        return mainWord;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Relation[] getIncomingRelations() {
        return incomingRelations;
    }

    public Relation[] getOutgoingRelations() {
        return outgoingRelations;
    }

    public Node getNodeWithEid(int eid) {
        for (Node node : nodes) {
            if (node.getEid() == eid) {
                return node;
            }
        }
        return null;
    }

    public List<Node> getRelationsNode2(RelationType relationType, RelationDirection direction, int minimalWeight, int count) {
        Relation[] relations;
        switch (direction) {
            case INCOMING:
                relations = incomingRelations;
                break;
            case OUTGOING:
                relations = outgoingRelations;
                break;
            default:
                return null;
        }

        int i = 0;
        List<Node> nodeList = new ArrayList<>();
        for (Relation relation : relations) {
            if (relation != null) {
                Node node2 = relation.getNode2();
                if (node2 != null && relation.getRelationType() == relationType && (relation.getWeight() >= minimalWeight || relation.getWeight() < 0)) {
                    nodeList.add(node2);
                    i++;
                    if (i >= count) break;
                }
            }
        }
        return nodeList;
    }

    public List<Node> getRelationsNode2(RelationType relationType, RelationDirection direction, int minimalWeight) {
        Relation[] relations;
        switch (direction) {
            case INCOMING:
                relations = incomingRelations;
                break;
            case OUTGOING:
                relations = outgoingRelations;
                break;
            default:
                return null;
        }

        List<Node> nodeList = new ArrayList<>();
        for (Relation relation : relations) {
            if (relation != null) {
                Node node2 = relation.getNode2();
                if (node2 != null && relation.getRelationType() == relationType && (relation.getWeight() >= minimalWeight || relation.getWeight() < 0)) {
                    nodeList.add(node2);
                }
            }
        }
        return nodeList;
    }

    public List<Node> getRelationsNode2(RelationType relationType, RelationDirection direction) {
        Relation[] relations;
        switch (direction) {
            case INCOMING:
                relations = incomingRelations;
                break;
            case OUTGOING:
                relations = outgoingRelations;
                break;
            default:
                return null;
        }

        List<Node> nodeList = new ArrayList<>();
        for (Relation relation : relations) {
            if (relation != null) {
                Node node2 = relation.getNode2();
                if (node2 != null && relation.getRelationType() == relationType) {
                    nodeList.add(node2);
                }
            }
        }
        return nodeList;
    }


    public List<Relation> getRelations(RelationType relationType, RelationDirection direction, int minimalWeight) {
        Relation[] relations;
        switch (direction) {
            case INCOMING:
                relations = incomingRelations;
                break;
            case OUTGOING:
                relations = outgoingRelations;
                break;
            default:
                return null;
        }

        List<Relation> relationList = new ArrayList<>();
        for (Relation relation : relations) {
            if (relation != null) {
                Node node2 = relation.getNode2();
                if (node2 != null && relation.getRelationType() == relationType && (relation.getWeight() >= minimalWeight || relation.getWeight() < 0)) {
                    relationList.add(relation);
                }
            }
        }
        return relationList;
    }

    public List<Relation> getRelations(RelationType relationType, RelationDirection direction) {
        Relation[] relations;
        switch (direction) {
            case INCOMING:
                relations = incomingRelations;
                break;
            case OUTGOING:
                relations = outgoingRelations;
                break;
            default:
                return null;
        }

        List<Relation> relationList = new ArrayList<>();
        for (Relation relation : relations) {
            if (relation != null) {
                Node node2 = relation.getNode2();
                if (node2 != null && relation.getRelationType() == relationType) {
                    relationList.add(relation);
                }
            }
        }
        return relationList;
    }

    public Relation getPreciseRelation(RelationType relationType, String word2, RelationDirection direction) {
        Relation[] relations;
        switch (direction) {
            case INCOMING:
                relations = incomingRelations;
                break;
            case OUTGOING:
                relations = outgoingRelations;
                break;
            default:
                return null;
        }
        for (Relation relation : relations) {
            if (relation != null) {
                Node node2 = relation.getNode2();
                if (node2 != null && node2.getName().equals(word2) && relation.getRelationType() == relationType) {
                    return relation;
                }
            }
        }
        return null;
    }

    public boolean relationExists(RelationType relationType, String word2, RelationDirection direction) {
        return getPreciseRelation(relationType, word2, direction) != null;
    }

    public boolean relationExists(RelationType relationType, String word2) {
        return relationExists(relationType, word2, RelationDirection.INCOMING) || relationExists(relationType, word2, RelationDirection.OUTGOING);
    }

    @Override
    public String toString() {
        return "LexicalGraph{" +
                "nodes=" + Arrays.toString(nodes) +
                ", incomingRelations=" + Arrays.toString(incomingRelations) +
                ", outgoingRelations=" + Arrays.toString(outgoingRelations) +
                '}';
    }
}
