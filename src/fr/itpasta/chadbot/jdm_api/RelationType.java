package fr.itpasta.chadbot.jdm_api;

import java.io.Serializable;

public enum RelationType implements Serializable {
    R_ANY(-1, "r_any"),
    R_ASSOCIATED(0,"r_associated"),
    R_RAFF_SEM(1,"r_raff_sem"),
    R_RAFF_MORPHO(2,"r_raff_morpho"),
    R_DOMAIN(3,"r_domain"),
    R_POS(4,"r_pos"),
    R_SYN(5,"r_syn"),
    R_ISA(6,"r_isa"),
    R_ANTO(7,"r_anto"),
    R_HYPO(8,"r_hypo"),
    R_HAS_PART(9,"r_has_part"),
    R_HOLO(10,"r_holo"),
    R_LOCUTION(11,"r_locution"),
    R_FLPOT(12,"r_flpot"),
    R_AGENT(13,"r_agent"),
    R_PATIENT(14,"r_patient"),
    R_LIEU(15,"r_lieu"),
    R_INSTR(16,"r_instr"),
    R_CARAC(17,"r_carac"),
    R_DATA(18,"r_data"),
    R_LEMMA(19,"r_lemma"),
    R_HAS_MAGN(20,"r_has_magn"),
    R_HAS_ANTIMAGN(21,"r_has_antimagn"),
    R_FAMILY(22,"r_family"),
    R_CARAC_1(23,"r_carac-1"),
    R_AGENT_1(24,"r_agent-1"),
    R_INSTR_1(25,"r_instr-1"),
    R_PATIENT_1(26,"r_patient-1"),
    R_DOMAIN_1(27,"r_domain-1"),
    R_LIEU_1(28,"r_lieu-1"),
    R_CHUNK_PRED(29,"r_chunk_pred"),
    R_LIEU_ACTION(30,"r_lieu_action"),
    R_ACTION_LIEU(31,"r_action_lieu"),
    R_SENTIMENT(32,"r_sentiment"),
    R_ERROR(33,"r_error"),
    R_MANNER(34,"r_manner"),
    R_MEANING_GLOSE(35,"r_meaning/glose"),
    R_INFOPOT(36,"r_infopot"),
    R_TELIC_ROLE(37,"r_telic_role"),
    R_AGENTIF_ROLE(38,"r_agentif_role"),
    R_VERBE_ACTION(39,"r_verbe-action"),
    R_ACTION_VERBE(40,"r_action-verbe"),
    R_HAS_CONSEQ(41,"r_has_conseq"),
    R_HAS_CAUSATIF(42,"r_has_causatif"),
    R_ADJ_VERBE(43,"r_adj-verbe"),
    R_VERBE_ADJ(44,"r_verbe-adj"),
    R_CHUNK_SUJET(45,"r_chunk_sujet"),
    R_CHUNK_OBJET(46,"r_chunk_objet"),
    R_CHUNK_LOC(47,"r_chunk_loc"),
    R_CHUNK_INSTR(48,"r_chunk_instr"),
    R_TIME(49,"r_time"),
    R_OBJECT_TO_MATER(50,"r_object>mater"),
    R_MATER_TO_OBJECT(51,"r_mater>object"),
    R_SUCCESSEUR_TIME(52,"r_successeur-time"),
    R_MAKE(53,"r_make"),
    R_PRODUCT_OF(54,"r_product_of"),
    R_AGAINST(55,"r_against"),
    R_AGAINST_1(56,"r_against-1"),
    R_IMPLICATION(57,"r_implication"),
    R_QUANTIFICATEUR(58,"r_quantificateur"),
    R_MASC(59,"r_masc"),
    R_FEM(60,"r_fem"),
    R_EQUIV(61,"r_equiv"),
    R_MANNER_1(62,"r_manner-1"),
    R_AGENTIVE_IMPLICATION(63,"r_agentive_implication"),
    R_HAS_INSTANCE(64,"r_has_instance"),
    R_VERB_REAL(65,"r_verb_real"),
    R_CHUNK_HEAD(66,"r_chunk_head"),
    R_SIMILAR(67,"r_similar"),
    R_SET_TO_ITEM(68,"r_set>item"),
    R_ITEM_TO_SET(69,"r_item>set"),
    R_PROCESSUS_TO_AGENT(70,"r_processus>agent"),
    R_VARIANTE(71,"r_variante"),
    R_SYN_STRICT(72,"r_syn_strict"),
    R_IS_SMALLER_THAN(73,"r_is_smaller_than"),
    R_IS_BIGGER_THAN(74,"r_is_bigger_than"),
    R_ACCOMP(75,"r_accomp"),
    R_PROCESSUS_TO_PATIENT(76,"r_processus>patient"),
    R_VERB_PPAS(77,"r_verb_ppas"),
    R_COHYPO(78,"r_cohypo"),
    R_VERB_PPRE(79,"r_verb_ppre"),
    R_PROCESSUS_TO_INSTR(80,"r_processus>instr"),
    R_PREF_FORM(81,"r_pref_form"),
    R_INTERACT_WITH(82,"r_interact_with"),
    R_DER_MORPHO(99,"r_der_morpho"),
    R_HAS_AUTEUR(100,"r_has_auteur"),
    R_HAS_PERSONNAGE(101,"r_has_personnage"),
    R_CAN_EAT(102,"r_can_eat"),
    R_HAS_ACTORS(103,"r_has_actors"),
    R_DEPLAC_MODE(104,"r_deplac_mode"),
    R_HAS_INTERPRET(105,"r_has_interpret"),
    R_HAS_COLOR(106,"r_has_color"),
    R_HAS_CIBLE(107,"r_has_cible"),
    R_HAS_SYMPTOMES(108,"r_has_symptomes"),
    R_HAS_PREDECESSEUR_TIME(109,"r_has_predecesseur-time"),
    R_HAS_DIAGNOSTIC(110,"r_has_diagnostic"),
    R_HAS_PREDECESSEUR_SPACE(111,"r_has_predecesseur-space"),
    R_HAS_SUCCESSEUR_SPACE(112,"r_has_successeur-space"),
    R_HAS_SOCIAL_TIE_WITH(113,"r_has_social_tie_with"),
    R_TRIBUTARY(114,"r_tributary"),
    R_SENTIMENT_1(115,"r_sentiment-1"),
    R_LINKED_WITH(116,"r_linked-with"),
    R_FONCTEUR(117,"r_foncteur"),
    R_COMPARISON(118,"r_comparison"),
    R_BUT(119,"r_but"),
    R_BUT_1(120,"r_but-1"),
    R_OWN(121,"r_own"),
    R_OWN_1(122,"r_own-1"),
    R_VERB_AUX(123,"r_verb_aux"),
    R_PREDECESSEUR_LOGIC(124,"r_predecesseur-logic"),
    R_SUCCESSEUR_LOGIC(125,"r_successeur-logic"),
    R_ISA_INCOMPATIBLE(126,"r_isa-incompatible"),
    R_INCOMPATIBLE(127,"r_incompatible"),
    R_NODE2RELNODE_IN(128,"r_node2relnode-in"),
    R_REQUIRE(129,"r_require"),
    R_IS_INSTANCE_OF(130,"r_is_instance_of"),
    R_IS_CONCERNED_BY(131,"r_is_concerned_by"),
    R_SYMPTOMES_1(132,"r_symptomes-1"),
    R_UNITS(133,"r_units"),
    R_PROMOTE(134,"r_promote"),
    R_CIRCUMSTANCES(135,"r_circumstances"),
    R_HAS_AUTEUR_1(136,"r_has_auteur-1"),
    R_PROCESSUS_TO_AGENT_1(137,"r_processus>agent-1"),
    R_PROCESSUS_TO_PATIENT_1(138,"r_processus>patient-1"),
    R_PROCESSUS_TO_INSTR_1(139,"r_processus>instr-1"),
    R_NODE2RELNODE_OUT(140,"r_node2relnode-out"),
    R_POURVOYEUR(148,"r_pourvoyeur"),
    R_COMPL_AGENT(149,"r_compl_agent"),
    R_HAS_BENEFICIAIRE(150,"r_has_beneficiaire"),
    R_DESCEND_DE(151,"r_descend_de"),
    R_DOMAIN_SUBST(152,"r_domain_subst"),
    R_HAS_PROP(153,"r_has_prop"),
    R_ACTIV_VOICE(154,"r_activ_voice"),
    R_MAKE_USE_OF(155,"r_make_use_of"),
    R_IS_USED_BY(156,"r_is_used_by"),
    R_ADJ_NOMPROP(157,"r_adj-nomprop"),
    R_NOMPROP_ADJ(158,"r_nomprop-adj"),
    R_ADJ_ADV(159,"r_adj-adv"),
    R_ADV_ADJ(160,"r_adv-adj"),
    R_HOMOPHONE(161,"r_homophone"),
    R_POTENTIAL_CONFUSION_WITH(162,"r_potential_confusion_with"),
    R_CONCERNING(163,"r_concerning"),
    R_ADJ_TO_NOM(164,"r_adj>nom"),
    R_NOM_TO_ADJ(165,"r_nom>adj"),
    R_OPINION_OF(166,"r_opinion_of"),
    R_HAS_VALUE(167,"r_has_value"),
    R_HAS_VALUE_SUPERIOR(168,"r_has_value>"),
    R_HAS_VALUE_INFERIOR(169,"r_has_value<"),
    R_SING_FORM(170,"r_sing_form"),
    R_CONTEXT(200,"r_context"),
    R_POS_SEQ(222,"r_pos_seq"),
    R_TRANSLATION(333,"r_translation"),
    R_LINK(444,"r_link"),
    R_COOCCURRENCE(555,"r_cooccurrence"),
    R_AKI(666,"r_aki"),
    R_WIKI(777,"r_wiki"),
    R_ANNOTATION_EXCEPTION(997,"r_annotation_exception"),
    R_ANNOTATION(998,"r_annotation"),
    R_INHIB(999,"r_inhib"),
    R_PREV(1000,"r_prev"),
    R_SUCC(1001,"r_succ"),
    R_TERMGROUP(1002,"r_termgroup"),
    R_RAFF_SEM_1(2000,"r_raff_sem-1"),
    R_LEARNING_MODEL(2001,"r_learning_model");

    private short id;
    private String name;

    RelationType(int id, String name) {
        this.id = (short) id;
        this.name = name;
    }

    public short getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RelationType getWithID(int id) {
        short idS = (short) id;
        for (RelationType relationType : RelationType.values()) {
            if (relationType.getID() == idS) {
                return relationType;
            }
        }
        return null;
    }

    public static RelationType getWithName(String name) {
        for (RelationType relationType : RelationType.values()) {
            if (relationType.getName().equals(name)) {
                return relationType;
            }
        }
        return null;
    }

    public static RelationType getWithName(String name, boolean ignoreCase) {
        if (ignoreCase) {
            for (RelationType relationType : RelationType.values()) {
                if (relationType.getName().equalsIgnoreCase(name)) {
                    return relationType;
                }
            }
            return null;
        } else {
            return getWithName(name);
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
