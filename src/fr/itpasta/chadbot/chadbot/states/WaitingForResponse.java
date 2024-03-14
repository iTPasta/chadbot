package fr.itpasta.chadbot.chadbot.states;

import fr.itpasta.chadbot.jdm_api.RelationType;
import fr.itpasta.chadbot.jdm_api.ResponseType;

public class WaitingForResponse extends ChadBotState {
    private RelationType relationType;
    private ResponseType responseType;

    public WaitingForResponse(RelationType relationType, ResponseType responseType) {
        super("WAITING_FOR_RESPONSE");
        this.relationType = relationType;
        this.responseType = responseType;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
