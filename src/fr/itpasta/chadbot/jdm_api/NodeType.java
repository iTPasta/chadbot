package fr.itpasta.chadbot.jdm_api;

import java.io.Serializable;

public enum NodeType implements Serializable {
    N_DEFAULT(0,"n_default"),
    N_TERM(1,"n_term"),
    N_FORM(2,"n_form"),
    N_POS(4,"n_pos"),
    N_CONCEPT(5,"n_concept"),
    N_FLPOT(6,"n_flpot"),
    N_CHUNK(8,"n_chunk"),
    N_QUESTION(9,"n_question"),
    N_RELATION(10,"n_relation"),
    N_RULE(11,"n_rule"),
    N_ANALOGY(12,"n_analogy"),
    N_COMMANDS(13,"n_commands"),
    N_SYNT_FUNCTION(14,"n_synt_function"),
    N_RELATION_NAME(15,"n_relation_name"),
    N_DATA(18,"n_data"),
    N_DATA_POT(36,"n_data_pot"),
    N_CONTEXT(200,"n_context"),
    N_POS_SEQ(222,"n_pos_seq"),
    N_LINK(444,"n_link"),
    N_AKI(666,"n_AKI"),
    N_WIKIPEDIA(777,"n_wikipedia"),
    N_GROUP(1002,"n_group");

    private short id;
    private String name;

    NodeType(int id, String name) {
        this.id = (short) id;
        this.name = name;
    }

    public short getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static NodeType getWithID(int id) {
        short idS = (short) id;
        for (NodeType nodeTypes : NodeType.values()) {
            if (nodeTypes.getID() == idS) {
                return nodeTypes;
            }
        }
        return null;
    }

    public static NodeType getWithName(String name) {
        for (NodeType nodeTypes : NodeType.values()) {
            if (nodeTypes.getName().equals(name)) {
                return nodeTypes;
            }
        }
        return null;
    }

    public static NodeType getWithName(String name, boolean ignoreCase) {
        if (ignoreCase) {
            for (NodeType nodeTypes : NodeType.values()) {
                if (nodeTypes.getName().equalsIgnoreCase(name)) {
                    return nodeTypes;
                }
            }
            return null;
        } else {
            return getWithName(name);
        }
    }
}
