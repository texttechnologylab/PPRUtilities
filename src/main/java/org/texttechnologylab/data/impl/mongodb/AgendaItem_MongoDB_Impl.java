package org.texttechnologylab.data.impl.mongodb;

import org.bson.Document;
import org.texttechnologylab.data.AgendaItem;
import org.texttechnologylab.data.PlenaryProtocol;
import org.texttechnologylab.data.Speech;
import org.texttechnologylab.data.impl.file.AgendaItem_File_Impl;

import java.util.List;

public class AgendaItem_MongoDB_Impl extends AgendaItem_File_Impl implements AgendaItem {

    private Document pDocument = null;

    public AgendaItem_MongoDB_Impl(PlenaryProtocol pProtocol, Document pDocument) {
        super(pProtocol.getFactory());
        this.pDocument = pDocument;
    }

    @Override
    public List<Speech> getSpeeches() {
        return getFactory().getSpeeches(this.getProtocol(), this);
    }

    @Override
    public String getIndex() {
        return pDocument.getString("index");
    }

    @Override
    public String getID() {
        return pDocument.getString("id");
    }

    @Override
    public String getTitle() {
        return pDocument.getString("title");
    }
}
