package org.texttechnologylab.data.impl.mongodb;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.texttechnologylab.data.*;
import org.texttechnologylab.data.impl.file.Speech_File_Impl;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Speech_MongoDB_Impl extends Speech_File_Impl implements Speech {

    private Document pDocument = null;

    public Speech_MongoDB_Impl(ParliamentFactory pFactory, Document pMongoDocument) {
        super(pFactory);
        this.pDocument = pMongoDocument;
    }

    public Document getDocument(){
        return pDocument;
    }

    public Speech_MongoDB_Impl(AgendaItem pAgenda, Node pNode) {
        super(pAgenda, pNode);
    }

    public Speech_MongoDB_Impl(AgendaItem pAgenda, String sID) {
        super(pAgenda, sID);
    }

    @Override
    public String getID() {
        return pDocument.getString("_id");
    }

    @Override
    public String getText() {
        return pDocument.getString("text");
    }

    @Override
    public String getPlainText() {
        return getText();
    }

    @Override
    public List<Text> getTexts() {
        List<Text> rList = new ArrayList<>(0);

        List<Document> pList = this.pDocument.getList("texts", Document.class);

        for(Document pDoc : pList) {
            if(pDoc.getString("type").equalsIgnoreCase("comment")){
                rList.add(new Comment_MongoDB_Impl(this.getFactory(), this.getSpeaker(), this, pDoc.getString("content")));
            }
            else{
                rList.add(new Text_MongoDB_Impl(this.getFactory(), this.getSpeaker(), this, pDoc.getString("content")));
            }
        }

        return rList;

    }

    @Override
    public Speaker getSpeaker() {
        String sSpeaker = this.pDocument.getString("speaker");

        return getFactory().getSpeaker(sSpeaker);

    }

    @Override
    public List<Comment> getComments() {

        List<Comment> rList = new ArrayList<>(0);

        this.pDocument.getList("comments", String.class);

        Iterator<Document> dIterator = this.getFactory().getMongoConnection().doQueryIterator(BasicDBObject.parse("{ \"_id\": \""+this.getID()+"\"}"), "speech");

        dIterator.forEachRemaining(d->{

            List<Document> dList = d.getList("texts", Document.class);

            dList.forEach(doc->{
                if(doc.getString("type").equalsIgnoreCase("comment")){
                    rList.add(new Comment_MongoDB_Impl(this.getFactory(), this.getSpeaker(), this, doc.getString("content")));
                }
            });

        });

        return rList;
    }

    @Override
    public PlenaryProtocol getProtocol() {
        return new PlenaryProtocol_MongoDB_Impl(this.getFactory(), (Document) pDocument.get("protocol"));
    }

    public void update(){
        try {
            this.pFactory.updateSpeech(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AgendaItem getAgendaItem() {
        return new AgendaItem_MongoDB_Impl(getProtocol(), pDocument.get("agenda", Document.class));
    }

}
