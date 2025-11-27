package org.texttechnologylab.data.impl.file;


import org.texttechnologylab.data.ParliamentFactory;
import org.texttechnologylab.data.Speaker;
import org.texttechnologylab.data.Speech;
import org.texttechnologylab.data.Text;

/**
 * Implementation of a text segment of a speech.
 * @author Giuseppe Abrami
 */
public class Text_File_Impl extends PlenaryObject_File_Impl implements Text {

    protected Speaker pSpeaker = null;
    protected Speech pSpeech = null;
    protected String sText = "";

    public Text_File_Impl(ParliamentFactory pFactory){
        super(pFactory);
    }

    /**
     * Constructor
     * @param pSpeaker
     * @param pSpeech
     * @param sText
     */
    public Text_File_Impl(Speaker pSpeaker, Speech pSpeech, String sText){
        this.pSpeaker = pSpeaker;
        this.pSpeech = pSpeech;
        this.sText = sText;

    }

    public Text_File_Impl(String sText){
        this.sText = sText;
    }

    @Override
    public Speech getSpeech() {
        return this.pSpeech;
    }

    @Override
    public Speaker getSpeaker() {
        return this.pSpeaker;
    }

    @Override
    public void setSpeech(Speech pSpeech) {
        this.pSpeech = pSpeech;
    }

    @Override
    public void setSpeaker(Speaker pSpeaker) {
        this.pSpeaker = pSpeaker;
    }

    @Override
    public String getContent() {
        return this.sText;
    }

    @Override
    public String getID() {
        return this.getSpeech().getID()+"-"+this.getContent().hashCode();
    }
}
