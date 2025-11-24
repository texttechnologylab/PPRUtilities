package org.texttechnologylab.data;

import org.bson.Document;
import org.texttechnologylab.database.MongoDBConfig;
import org.texttechnologylab.database.MongoDBConnectionHandler;
import org.w3c.dom.Node;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Interface for interaction with the plenary protocols.
 * @author Giuseppe Abrami
 */
public interface ParliamentFactory {


    MongoDBConnectionHandler getMongoConnection();

    /**
     * Return all speakers
     * @return
     */
    Set<Speaker> getSpeakers();

    /**
     * Return all speakers by Fraction
     * @param pFraction
     * @return
     */
    Set<Speaker> getSpeakers(Fraction pFraction);

    /**
     * Return all speakers by Protocol
     * @param pProtocol
     * @return
     */
    Set<Speaker> getSpeakers(PlenaryProtocol pProtocol);
    /**
     * Return all protocols
     * @return
     */
    Set<PlenaryProtocol> getProtocols();

    /**
     * Get Protocolls by WP
     * @return
     */
    Set<PlenaryProtocol> getProtocols(int iWP);

    /**
     * Get Protocolls by WP
     * @return
     */
    PlenaryProtocol getProtocol(int iIndex);

    /**
     * Add a protocol
     * @param pProtocol
     */
    void addProtocol(PlenaryProtocol pProtocol);

    /**
     * Return all fractions
     * @return
     */
    Set<Fraction> getFractions();

    /**
     * Return all parties
     * @return
     */
    Set<Party> getParties();

    /**
     * Get a specific Party. If the party does not exist, it will be created.
     * @param sName
     * @return
     */
    Party getParty(String sName);

    /**
     * Get a speaker based on its Name. If the speaker does not exist, he / she will be created
     * @param sName
     * @return
     */
    Speaker getSpeaker(String sName);



    void createDatabaseConnection(MongoDBConfig pConfig);

    void addSpeech(Speech pSpeech);

    void addComment(Comment pComment);

    void addSpeaker(Speaker pSpeaker);

    /**
     * Get a speaker based on a Node. If the speaker does not exist, he / she will be created
     * @param pNode
     * @return
     */
    Speaker getSpeaker(Node pNode);

    /**
     * Get a fraction based on its Name. If the fraction does not exist, it will be created
     * @param sName
     * @return
     */
    Fraction getFraction(String sName);

    /**
     * Get a fraction based on a Node. If the fraction does not exist, it will be created
     * @param pNode
     * @return
     */
    Fraction getFraction(Node pNode);

    /**
     * Get Members of an Party
     * @param pParty
     * @return
     */
    Set<Speaker> getMembers(Party pParty);

    /**
     * Get members of an Fraction
     * @param pFraction
     * @return
     */
    Set<Speaker> getMembers(Fraction pFraction);

    /**
     * Get AgendaItems of a Protocol
     * @param pProtocol
     * @return
     */
    List<AgendaItem> getAgendaItem(PlenaryProtocol pProtocol);

    /**
     * Get Speec based on its ID
     * @param sID
     * @return
     */
    Speech getSpeech(String sID);

    /**
     * Get Speeches
     * @param pProtocol
     * @param pItem
     * @return
     */
    List<Speech> getSpeeches(PlenaryProtocol pProtocol, AgendaItem pItem);

    /**
     * Get Speeches
     * @param pSpeaker
     * @return
     */
    List<Speech> getSpeeches(Speaker pSpeaker);

    /**
     * List Comments
     * @return
     */
    List<Comment> getComments();

    /**
     * List Comments by Speaker
     * @param pSpeaker
     * @return
     */
    List<Comment> getComments(Speaker pSpeaker);

    /**
     * Update Speech
     * @param pSpeech
     */
    void updateSpeech(Speech pSpeech);

    /**
     * Update Comment
     * @param pComment
     */
    void updateComment(Comment pComment);

    /**
     * Update Speaker
     * @param pSpeaker
     */
    void updateSpeaker(Speaker pSpeaker);

    /**
     * List Protocol by Duration
     * @param bDescending
     * @return
     */
    List<PlenaryProtocol> listByDate(boolean bDescending);

    /**
     * Perform Full Text Search
     * @param sValue
     * @return
     */
    List<Speech> fullTextSearch(String sValue);

    /**
     * Get Speakers by Speeches
     * @param pParty
     * @param pFraction
     * @return
     */
    List<Document> getSpeakerBySpeeches(Party pParty, Fraction pFraction);

    /**
     * Get Speakers by Speeches
     * @param pFromDate
     * @param pToDate
     * @param pParty
     * @param pFraction
     * @return
     */
    List<Document> getSpeakerBySpeeches(Date pFromDate, Date pToDate, Party pParty, Fraction pFraction);

    /**
     * Get AVG-Length of Speeches
     * @param pParty
     * @param pFraction
     * @return
     */
    List<Document> getAvgSpeeches(Party pParty, Fraction pFraction);

    /**
     * Get AVG-Length of Speeches
     * @param pDateFrom
     * @param pDateTo
     * @param pParty
     * @param pFraction
     * @return
     */
    List<Document> getAvgSpeeches(Date pDateFrom, Date pDateTo, Party pParty, Fraction pFraction);

    /**
     * Get AVG-Length of Speeches
     * @param pDate
     * @param pParty
     * @param pFraction
     * @return
     */
    List<Document> getAvgSpeeches(Date pDate, Party pParty, Fraction pFraction);
}
