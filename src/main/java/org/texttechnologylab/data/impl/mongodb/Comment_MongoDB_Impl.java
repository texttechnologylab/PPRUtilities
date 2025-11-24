package org.texttechnologylab.data.impl.mongodb;

import org.bson.Document;
import org.texttechnologylab.data.Comment;
import org.texttechnologylab.data.ParliamentFactory;
import org.texttechnologylab.data.Speaker;
import org.texttechnologylab.data.Speech;

public class Comment_MongoDB_Impl extends Text_MongoDB_Impl implements Comment {

    public Comment_MongoDB_Impl(ParliamentFactory pFactory, Speaker pSpeaker, Speech pSpeech, String sText) {
        super(pFactory, pSpeaker, pSpeech, sText);
    }

    public Comment_MongoDB_Impl(ParliamentFactory pFactory, Document pDocument) {
        super(pFactory, pDocument);
    }

    @Override
    public String getContent() {
        return pDocument.getString("text");
    }

    @Override
    public Speaker getSpeaker() {
        return this.getFactory().getSpeaker(pDocument.getString("speaker"));
    }

    @Override
    public Speech getSpeech() {
        if(pDocument.containsKey("speach")){
            pDocument.put("speech", pDocument.getString("speach"));
        }
        return this.getFactory().getSpeech(pDocument.getString("speech"));
    }


}
