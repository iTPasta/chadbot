package fr.itpasta.chadbot.jdm_api;

import java.io.Serializable;

public class Node implements Serializable {
    private int eid;
    private String name;
    private NodeType nodeType;
    private int weight;
    private String formattedName;

    public Node(int eid, String name, NodeType nodeType, int weight) {
        this.eid = eid;
        if (name.charAt(0) == '\'') name = name.substring(1);
        if (name.charAt(name.length() - 1) == '\'') name = name.substring(0, name.length() - 1);
        this.name = name;
        this.nodeType = nodeType;
        this.weight = weight;
        this.formattedName = null;
    }

    public Node(int eid, String name, int nodeTypeID, int weight) {
        this(eid, name, NodeType.getWithID(nodeTypeID), weight);
    }

    public Node(int eid, String name, int nodeTypeID, int weight, String formattedName) {
        this(eid, name, nodeTypeID, weight);
        this.formattedName = formattedName;
    }

    public Node(String eid, String name, String nodeTypeID, String weight, String formattedName) {
        this(Integer.parseInt(eid), name, Integer.parseInt(nodeTypeID), Integer.parseInt(weight), formattedName);
    }

    public Node(String eid, String name, String nodeTypeID, String weight) {
        this(Integer.parseInt(weight), name, Integer.parseInt(nodeTypeID), Integer.parseInt(weight));
    }

    public Node(String[] nodeStrInfos) {
        this(nodeStrInfos[1], nodeStrInfos[2], nodeStrInfos[3], nodeStrInfos[4], nodeStrInfos.length >= 6 ? nodeStrInfos[5] : null);
    }

    public int getEid() {
        return eid;
    }

    public String getName() {
        return name;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public int getWeight() {
        return weight;
    }

    public String getFormattedName() {
        return formattedName;
    }

    @Override
    public String toString() {
        return "{" + Integer.toString(eid) + ", " + name + "}";
    }
}
