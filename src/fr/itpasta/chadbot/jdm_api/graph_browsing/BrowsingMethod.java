package fr.itpasta.chadbot.jdm_api.graph_browsing;

import fr.itpasta.chadbot.jdm_api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.itpasta.chadbot.Main.api;

public enum BrowsingMethod {
    HAS_CAPACITY_BECAUSE_OWN1(RelationType.R_OWN, RelationType.R_INSTR_1, RelationType.R_AGENT_1),
    HAS_CAPACITY_BECAUSE_OWN2(RelationType.R_HAS_PART, RelationType.R_INSTR_1, RelationType.R_AGENT_1),
    DEDUCTION(RelationType.R_ISA, RelationType.R_ANY, RelationType.R_ANY),
    SYNONYM(RelationType.R_SYN, RelationType.R_ANY, RelationType.R_ANY),
    INDUCTION(RelationType.R_HYPO, RelationType.R_ANY, RelationType.R_ANY),

    OWN_BECAUSE_HAS_CAPACITY1(RelationType.R_AGENT_1, RelationType.R_INSTR, RelationType.R_OWN),
    OWN_BECAUSE_HAS_CAPACITY2(RelationType.R_AGENT_1, RelationType.R_INSTR, RelationType.R_HAS_PART);

    private final static int DEFAULT_MINIMAL_WEIGHT = 10;
    private final static int DEFAULT_MAX_COUNT = 20;
    private final static boolean DEFAULT_DEEP = false;
    private final RelationType R1;
    private final RelationType R2;
    private final RelationType R3;
    private final int minimalWeight;
    private final int maxCount;
    private final boolean deep;

    BrowsingMethod(RelationType R1, RelationType R2, RelationType R3, int minimalWeight, int maxCount, boolean deep) {
        assert R1 != RelationType.R_ANY;
        this.R1 = R1;
        this.R2 = R2;
        this.R3 = R3;
        this.minimalWeight = minimalWeight;
        this.maxCount = maxCount;
        this.deep = deep;
    }

    BrowsingMethod(RelationType R1, RelationType R2, RelationType R3) {
        this(R1, R2, R3, DEFAULT_MINIMAL_WEIGHT, DEFAULT_MAX_COUNT, DEFAULT_DEEP);
    }

    public static Proof applyAll(LexicalGraph lexicalGraph, String word2, RelationType relationType) {
        Proof proof;
        if ((proof = checkDirectRelation(lexicalGraph, word2, relationType)).getValidity() != Validity.UNKNOWN) return proof;
        BrowsingMethod[] browsingMethods = BrowsingMethod.values();
        int i = 0;
        while (proof.getValidity() == Validity.UNKNOWN && i < browsingMethods.length) {
            proof = browsingMethods[i].apply(lexicalGraph, word2, relationType);
            i++;
        }
        return proof;
    }

    public static Proof applyAll(String word1, String word2, RelationType relationType) throws IOException {
        return applyAll(api.getLexicalGraph(word1).getO1(), word2, relationType);
    }

    public Proof apply(LexicalGraph lexicalGraph, String word2, RelationType relationType) {
        assert R2 == RelationType.R_ANY || relationType == R2;
        assert relationType != RelationType.R_ANY;
        return generateProof(lexicalGraph, word2, R1, relationType, R3, this);
    }

    public Proof apply(String word1, String word2, RelationType relationType) throws IOException {
        return apply(api.getLexicalGraph(word1).getO1(), word2, relationType);
    }

    private static Proof checkDirectRelation(
            LexicalGraph lexicalGraph,
            String word2,
            RelationType relationType
    ) {
        Relation relation = lexicalGraph.getPreciseRelation(relationType, word2, RelationDirection.OUTGOING);
        if (relation != null) {
            return new Proof(Proof.makeExplanation(
                    lexicalGraph.getMainWord(),
                    word2,
                    relationType,
                    RelationDirection.OUTGOING,
                    relation.getWeight() >= 0 ? Validity.VALID : Validity.INVALID
            ));
        } else {
            return new Proof();
        }
    }


    private static Proof generateProof(
            LexicalGraph lexicalGraph,
            String word2,
            RelationType R1,
            RelationType R2,
            RelationType R3,
            boolean deep,
            int minimalWeight,
            int count
    ) {
        // il ne faut pas faire la méthode de relation directe ici
        RelationDirection relationDirection = R1 == RelationType.R_HYPO ? RelationDirection.INCOMING : RelationDirection.OUTGOING;
        R1 = R1 == RelationType.R_HYPO ? RelationType.R_ISA : R1;
        R3 = R3 == RelationType.R_ANY ? R2 : R3;
        Relation relation = lexicalGraph.getPreciseRelation(R2, word2, relationDirection);
        if (relation != null) {
            return new Proof(Proof.makeExplanation(
                    lexicalGraph.getMainWord(),
                    word2,
                    R3,
                    RelationDirection.OUTGOING,
                    relation.getWeight() >= 0 ? Validity.VALID : Validity.INVALID
            ));
        } else {
            List<Node> node2isa = lexicalGraph.getRelationsNode2(R1, relationDirection, minimalWeight, count);
            List<String> nodes2isaNames = new ArrayList<>();
            for (Node node : node2isa) {
                nodes2isaNames.add(node.getName());
            }
            List<LexicalGraph> deepGraphs1 = new ArrayList<>(RelationsRequest.ask(20, nodes2isaNames));
            for (LexicalGraph deepGraph1 : deepGraphs1) {
                Relation deepRelation1 = deepGraph1.getPreciseRelation(R2, word2, relationDirection);
                if (deepRelation1 != null) {
                    return new Proof(List.of(
                            Proof.makeExplanation(
                                    lexicalGraph.getMainWord(),
                                    deepGraph1.getMainWord(),
                                    R1,
                                    relationDirection
                            ),
                            Proof.makeExplanation(
                                    deepGraph1.getMainWord(),
                                    word2,
                                    R3,
                                    RelationDirection.OUTGOING,
                                    deepRelation1.getWeight() >= 0 ? Validity.VALID : Validity.INVALID
                            )
                    ));
                }
            }
            if (deep) {
                for (LexicalGraph deepGraph1 : deepGraphs1) {
                    List<Node> deepNode2isa = deepGraph1.getRelationsNode2(R1, relationDirection, minimalWeight, count);
                    List<String> deepNodes2isaNames = new ArrayList<>();
                    for (Node node : deepNode2isa) {
                        nodes2isaNames.add(node.getName());
                    }
                    List<LexicalGraph> deepGraphs2 = new ArrayList<>(RelationsRequest.ask(20, deepNodes2isaNames));
                    for (LexicalGraph deepGraph2 : deepGraphs2) {
                        Relation deepRelation2 = deepGraph2.getPreciseRelation(R2, word2, relationDirection);
                        if (deepRelation2 != null) {
                            return new Proof(List.of(
                                    Proof.makeExplanation(
                                            lexicalGraph.getMainWord(),
                                            deepGraph1.getMainWord(),
                                            R1,
                                            relationDirection
                                    ),
                                    Proof.makeExplanation(
                                            deepGraph1.getMainWord(),
                                            deepGraph2.getMainWord(),
                                            R1,
                                            relationDirection
                                    ),
                                    Proof.makeExplanation(
                                            deepGraph2.getMainWord(),
                                            word2,
                                            R3,
                                            RelationDirection.OUTGOING,
                                            deepRelation2.getWeight() >= 0 ? Validity.VALID : Validity.INVALID
                                    )
                            ));
                        }
                    }
                }
            }
        }
        return new Proof();
    }

    private static Proof generateProof(LexicalGraph lexicalGraph, String word2, RelationType R1, RelationType R2, RelationType R3, BrowsingMethod browsingMethod) {
        return generateProof(
                lexicalGraph,
                word2,
                R1,
                R2,
                R3,
                browsingMethod.isDeep(),
                browsingMethod.getMinimalWeight(),
                browsingMethod.getMaxCount()
        );
    }

    public RelationType getR1() {
        return R1;
    }

    public RelationType getR2() {
        return R2;
    }

    public RelationType getR3() {
        return R3;
    }

    public int getMinimalWeight() {
        return minimalWeight;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean isDeep() {
        return deep;
    }
}
