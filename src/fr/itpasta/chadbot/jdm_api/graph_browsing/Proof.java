package fr.itpasta.chadbot.jdm_api.graph_browsing;

import fr.itpasta.chadbot.jdm_api.RelationDirection;
import fr.itpasta.chadbot.jdm_api.RelationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.itpasta.chadbot.Main.save;

public class Proof {
    private List<Explanation> explanations;

    public Proof() {
        this.explanations = new ArrayList<>();
    }

    public Proof(List<Explanation> explanations) {
        this.explanations = explanations;
    }

    public Proof(Explanation explanation) {
        this(List.of(explanation));
    }

    public List<Explanation> getExplanations() {
        return explanations;
    }

    public Validity getValidity() {
        return explanations.isEmpty() ? Validity.UNKNOWN : explanations.get(explanations.size() - 1).getValidity();
    }

    private static <T> T getRandomFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = explanations.size();
        for (int i = 0; i < size; i++) {
            if (explanations.get(i).toString() != null) {
                if (i != size - 1) {
                    sb.append(explanations.get(i)).append(", ");
                } else {
                    sb.append(explanations.get(i)).append(".");
                }
            } else {
                return null;
            }
        }
        return sb.toString();
    }*/

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = explanations.size();
        for (int i = 0; i < size; i++) {
            if (explanations.get(i).toString() != null) {
                sb.append(explanations.get(i));
                if (i != size - 1) {
                    sb.append(", ");
                } else {
                    sb.append(".");
                }
            } else {
                return null;
            }
        }
        return sb.toString();
    }

    public static Explanation makeExplanation(String word1, String word2, RelationType relationType, RelationDirection relationDirection) {
        return new Explanation(word1, word2, relationType, relationDirection, Validity.VALID);
    }

    public static Explanation makeExplanation(String word1, String word2, RelationType relationType, RelationDirection relationDirection, Validity validity) {
        return new Explanation(word1, word2, relationType, relationDirection, validity);
    }

    public static class Explanation {
        private String word1;
        private String word2;
        private RelationType relationType;
        private RelationDirection relationDirection;
        private Validity validity;

        public Explanation(String word1, String word2, RelationType relationType, RelationDirection relationDirection, Validity validity) {
            this.word1 = word1;
            this.word2 = word2;
            this.relationType = relationType;
            this.relationDirection = relationDirection;
            this.validity = validity;
            if (relationType == RelationType.R_ISA && relationDirection == RelationDirection.INCOMING) {
                relationType = RelationType.R_HYPO;
                relationDirection = RelationDirection.OUTGOING;
            }
        }

        public String getWord1() {
            return word1;
        }

        public String getWord2() {
            return word2;
        }

        public RelationType getRelationType() {
            return relationType;
        }

        public RelationDirection getRelationDirection() {
            return relationDirection;
        }

        public Validity getValidity() {
            return validity;
        }

        /*@Override
        public String toString() {
            List<String> positivesResponses = save.getPositivesResponses(relationType);
            if (!positivesResponses.isEmpty()) return getRandomFromList(positivesResponses)
                    .replace("$a", word1)
                    .replace("$z", word2);
            else return null;
        }*/

        public String toString() {
            return (validity == Validity.INVALID ? "non(" : "")
                    + word1 + " " + relationType.getName() + " " + word2
                    + (validity == Validity.INVALID ? ")" : "");
        }
    }
}
