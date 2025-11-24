package org.texttechnologylab.database;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.texttechnologylab.data.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for MongoHelper Methods
 * @author Giuseppe Abrami
 */
public class MongoHelper {

    public static Document toMongoDocument(Speaker speaker)  {

        Document mongoDocument = new Document();
        mongoDocument.put("_id", speaker.getID());
        mongoDocument.put("name", speaker.getName());
        mongoDocument.put("firstName", speaker.getFirstName());
        mongoDocument.put("title", speaker.getTitle());
        mongoDocument.put("geburtsdatum", speaker.getGeburtsdatum());
        mongoDocument.put("geburtsort", speaker.getGeburtsort());
        mongoDocument.put("sterbedatum", speaker.getSterbedatum());
        mongoDocument.put("geschlecht", speaker.getGeschlecht());
        mongoDocument.put("beruf", speaker.getBeruf());
        mongoDocument.put("akademischertitel", speaker.getAkademischerTitel());
        mongoDocument.put("familienstand", speaker.getFamilienstand());
        mongoDocument.put("religion", speaker.getReligion());

        List<Integer> iAbsendes = new ArrayList<>();
        for (PlenaryProtocol absence : speaker.getAbsences()) {
            iAbsendes.add(absence.getIndex());
        }

        mongoDocument.put("absence", iAbsendes);
        if(speaker.getParty()!=null){
            mongoDocument.put("party", speaker.getParty().getName());
        }
        if(speaker.getFraction()!=null){
            mongoDocument.put("fraction", speaker.getFraction().getName());
        }
        mongoDocument.put("role", speaker.getRole());
        return mongoDocument;


    }

    /**
     * Method to convert a Speech to an MongoDocument
     * @param pSpeech
     * @return
     */
    public static Document toMongoDocument(Speech pSpeech) throws JSONException {

        // creating a empty MongoDocument and add attributes
        Document mongoDocument = new Document();
        mongoDocument.put("_id", pSpeech.getID());
        mongoDocument.put("text", pSpeech.getPlainText());
        mongoDocument.put("speaker", pSpeech.getSpeaker().getID());

        List texts = new ArrayList<>();


        for (Text text : pSpeech.getTexts()) {
            Document textDocument = new Document();
            textDocument.append("id", text.getID());
            textDocument.append("content", text.getContent());
            textDocument.append("type", (text instanceof Comment) ? "comment" : "text");
            texts.add(textDocument);
        }

        mongoDocument.put("texts", texts);

        BasicDBObject protocolObject = new BasicDBObject();
        PlenaryProtocol pProtocol = pSpeech.getProtocol();

        protocolObject.put("date", pProtocol.getDate().getTime());
        protocolObject.put("starttime", pProtocol.getStartTime().getTime());
        protocolObject.put("endtime", pProtocol.getEndTime().getTime());
        protocolObject.put("index", pProtocol.getIndex());
        protocolObject.put("title", pProtocol.getTitle());
        protocolObject.put("place", pProtocol.getPlace());
        protocolObject.put("wp", pProtocol.getWahlperiode());

        mongoDocument.put("protocol", protocolObject);

        AgendaItem pItem = pSpeech.getAgendaItem();

        JSONObject agendaItem = new JSONObject();
        agendaItem.put("id", pItem.getID());
        agendaItem.put("index", pItem.getIndex());
        agendaItem.put("title", pItem.getTitle());

        mongoDocument.put("agenda", Document.parse(agendaItem.toString()));

        mongoDocument.put("speaker", pSpeech.getSpeaker().getID());

        return mongoDocument;

    }


    /**
     * Method to convert a Comment to an MongoDocument
     * @param pComment
     * @return
     */
    public static Document toMongoDocument(Comment pComment) {

        // creating a empty MongoDocument and add attributes
        Document mongoDocument = new Document();
        mongoDocument.put("_id", pComment.getID());
        mongoDocument.put("text", pComment.getContent());
        mongoDocument.put("speaker", pComment.getSpeaker()!=null ? pComment.getSpeaker().getID() : "");
        mongoDocument.put("speech", pComment.getSpeech()!=null ? pComment.getSpeech().getID() : "");

        return mongoDocument;

    }

}
