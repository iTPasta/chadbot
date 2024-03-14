package fr.itpasta.chadbot.jdm_api;

import java.io.Serializable;
import java.util.HashMap;

public class Relation implements Serializable {
    private int rid;
    private Node node1;
    private Node node2;
    private RelationType relationType;
    private int weight;

    public Relation(int rid, int eid1, int eid2, RelationType relationType, int weight, HashMap<Integer, Node> nodesMap) {
        this.rid = rid;
        this.node1 = nodesMap.get(eid1);
        this.node2 = nodesMap.get(eid2);
        this.relationType = relationType;
        this.weight = weight;
        //this.relationDirection = relationDirection;
    }

    public Relation(int rid, int eid1, int eid2, int relationTypeID, int weight, HashMap<Integer, Node> nodesMap) {
        this(rid, eid1, eid2, RelationType.getWithID(relationTypeID), weight, nodesMap);
    }

    public Relation(String rid, String eid1, String eid2, String relationTypeID, String weight, HashMap<Integer, Node> nodesMap) {
        this(
                Integer.parseInt(rid),
                Integer.parseInt(eid1),
                Integer.parseInt(eid2),
                Integer.parseInt(relationTypeID),
                Integer.parseInt(weight),
                nodesMap
        );
    }

    public Relation(String[] relationStrInfos, HashMap<Integer, Node> nodesMap) {
        this(relationStrInfos[1], relationStrInfos[2], relationStrInfos[3], relationStrInfos[4], relationStrInfos[5], nodesMap);
    }

    public int getRid() {
        return rid;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "rid=" + rid +
                ", node1=" + node1 +
                ", node2=" + node2 +
                ", relationType=" + relationType +
                ", weight=" + weight +
                '}';
    }

    //public RelationDirection getRelationDirection() {
    //    return relationDirection;
    //}
}
