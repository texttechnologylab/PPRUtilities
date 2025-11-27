package org.texttechnologylab.data.impl.mongodb;

import org.bson.Document;
import org.texttechnologylab.data.ParliamentFactory;
import org.texttechnologylab.data.Speaker;
import org.texttechnologylab.data.Speech;
import org.texttechnologylab.data.Text;
import org.texttechnologylab.data.impl.file.Text_File_Impl;

public class Text_MongoDB_Impl extends Text_File_Impl implements Text {

    protected Document pDocument = null;

    public Text_MongoDB_Impl(ParliamentFactory parliamentFactory, Document pDocument){
        super(parliamentFactory);
        this.pDocument = pDocument;
    }

    public Text_MongoDB_Impl(ParliamentFactory pFactory, Speaker pSpeaker, Speech pSpeech, String sText) {
        super(pSpeaker, pSpeech, sText);
        this.pFactory = pFactory;
    }

}
