package fr.itpasta.chadbot.generic;

import fr.itpasta.chadbot.jdm_api.RelationType;

public class GenericRelation {
    private String word1;
    private String word2;
    private RelationType relationType;

    public GenericRelation(String word1, String word2, RelationType relationType) {
        this.word1 = word1;
        this.word2 = word2;
        this.relationType = relationType;
    }

    public GenericRelation(String word1, String word2, String relationTypeName) {
        this(word1, word2, RelationType.getWithName(relationTypeName));
    }

    public GenericRelation(String word1, String word2, int relationTypeID) {
        this(word1, word2, RelationType.getWithID(relationTypeID));
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
}
