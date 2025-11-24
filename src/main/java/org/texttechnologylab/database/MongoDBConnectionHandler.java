package org.texttechnologylab.database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.texttechnologylab.data.Comment;
import org.texttechnologylab.data.Speaker;
import org.texttechnologylab.data.Speech;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Class for interaction with a MongoDB
 * @author Giuseppe Abrami
 */
public class MongoDBConnectionHandler {

    /**
     *  MongoDBConfig Object
     */
    private MongoDBConfig pConfig = null;

    /**
     *  The connection with the MongoDB
     */
    private MongoClient pClient = null;

    /**
     * The object for the selected Database
     */
    private MongoDatabase pDatabase = null;

    /**
     * Amount of all Collections
     */
    private MongoCollection<Document> pCollection = null;

    /**
     * Constructor
     * @param pConfig
     */
    public MongoDBConnectionHandler(MongoDBConfig pConfig){
        this.pConfig = pConfig;
        init();
    }


    /**
     * Interne Methode zur Rückgabe der Configuration
     * @return
     */
    private MongoDBConfig getConfiguration(){
        return this.pConfig;
    }

    /**
     * Internal method for establishing the connection
     */
    private void init(){

        ServerAddress pAdress = new ServerAddress(getConfiguration().getMongoHostname(), getConfiguration().getMongoPort());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        // Es können mehrere Hosts angegeben werden
                        builder.hosts(List.of(pAdress)))
                // einzelne Einstellungen für die Verbindung
                .applyToSocketSettings(builder->builder.connectTimeout(5, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS))
                .applyToConnectionPoolSettings(builder->{
                    builder.maxConnecting(5);
                    builder.maxConnectionIdleTime(60, TimeUnit.SECONDS);
                    builder.maxConnectionLifeTime(30, TimeUnit.SECONDS);
                    builder.maxWaitTime(30, TimeUnit.SECONDS);
                })
                // Authentifizierung
                .credential(MongoCredential.createCredential(getConfiguration().getMongoUsername(), getConfiguration().getMongoDatabase(), getConfiguration().getMongoPassword().toCharArray()))
                .build();
        // Herstellen der Verbindung und fixierung im mongoClient
        this.pClient = MongoClients.create(settings);

        // select database
        pDatabase = pClient.getDatabase(pConfig.getMongoDatabase());


        // select database
        pDatabase = pClient.getDatabase(pConfig.getMongoDatabase());

        // select default connection
        pCollection = pDatabase.getCollection(pConfig.getMongoCollection());

        // some debug information
        System.out.println("Connect to "+pConfig.getMongoDatabase()+" on "+pConfig.getMongoHostname());

    }

    /**
     * Method to return the default Collection
     * @return MongoCollection
     */
    public MongoCollection getCollection(){
        return this.pCollection;
    }

    /**
     * Method to return the default Collection
     * @return MongoCollection
     */
    public MongoCollection getCollection(String sCollection){
        return this.pDatabase.getCollection(sCollection);
    }

    /**
     * Method to return the connected Database-Object
     * @return
     */
    public MongoDatabase getDatabase(){
        return this.pDatabase;
    }

    /**
     * Method to convert a Speech to an MongoDocument
     * @param pSpeech
     * @return
     */
    public Document getObject(Speech pSpeech){
        return getObject(pSpeech.getID(), "speech");
    }

    /**
     * Method to return a MongoDocument with a given ID
     * @param sID
     * @return Document
     */
    public Document getObject(String sID, String sCollection){

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", sID);

        FindIterable<Document> result = this.getCollection(sCollection).find(whereQuery);

        Document doc = null;

        MongoCursor<Document> it = result.iterator();

        while(it.hasNext()){
            doc = it.next();
        }

        return doc;

    }

    /**
     * Method to remove a Tweet from the database
     * @param pSpeech
     * @return
     */
    public boolean removeTweet(Speech pSpeech){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", pSpeech.getID());
        DeleteResult dResult = this.getCollection().deleteOne(whereQuery);
        return dResult.wasAcknowledged();
    }


    /**
     * Method to update (replace) a speech in the database
     * @param pSpeech
     * @return
     */
    public boolean update(Speech pSpeech) {

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", pSpeech.getID());
        UpdateResult uResult = null;
        try {
            uResult = this.getCollection("speech").replaceOne(whereQuery, MongoHelper.toMongoDocument(pSpeech));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(uResult==null){
            return false;
        }
        return uResult.getModifiedCount()>0;

    }

    /**
     * Method to update (replace) a comment in the database
     * @param pComment
     * @return
     */
    public boolean update(Comment pComment) {

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", pComment.getID());
        UpdateResult uResult = null;
        Document mongoDocument = MongoHelper.toMongoDocument(pComment);
        try {
            uResult = this.getCollection("comment").replaceOne(whereQuery, mongoDocument);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(uResult==null){
            return false;
        }
        else{
            if(uResult.getModifiedCount()==0){
                this.getCollection("comment").insertOne(mongoDocument);
            }
        }
        return uResult.getModifiedCount()>0;

    }

    /**
     * Method to update (replace) a Speaker in the database
     * @param pSpeaker
     * @return
     */
    public boolean update(Speaker pSpeaker) {

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", pSpeaker.getID());
        UpdateResult uResult = null;
        try {
            uResult = this.getCollection("speaker").replaceOne(whereQuery, MongoHelper.toMongoDocument(pSpeaker));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(uResult==null){
            return false;
        }
        return uResult.getModifiedCount()>0;

    }

    /**
     * Method to execute a query
     * @param query
     * @return
     */
    public FindIterable doQuery(BasicDBObject query){
        FindIterable result = this.getCollection().find(query);
        return result;
    }

    /**
     * Method to execute a query
     * @param query
     * @return
     */
    public MongoCursor doQueryIterator(BasicDBObject query, String sCollection){
        FindIterable result = this.getCollection(sCollection).find(query);
        return result.iterator();
    }

    /**
     * Method to execute a query
     * @param query
     * @return
     */
    public MongoCursor doQueryIterator(String query, String sCollection){
        return doQueryIterator(BasicDBObject.parse(query), sCollection);
    }

    /**
     * Method to execute a query
     * @param sField
     * @param sCollection
     * @param returnType
     * @return
     */
    public MongoCursor doQueryIteratorDistinct(String sField, Class returnType, String sCollection){
        DistinctIterable result = this.getCollection(sCollection).distinct(sField, returnType);
        return result.iterator();
    }

    /**
     * Method to count the results of a given query
     * @param query
     * @return
     */
    public long count(BasicDBObject query){
        return this.getCollection().countDocuments(query);
    }

    /**
     * Method to return all existing collections
     * @return
     */
    public Set<String> listCollections(){
        Set<String> rSet = new HashSet<>(0);
        rSet.addAll((Collection<? extends String>) this.getDatabase().listCollectionNames().spliterator());
        return rSet;
    }


    public Document insertSpeech(Speech pSpeech) {

        Document rDocument = null;

        rDocument = getObject(pSpeech.getID(), "speech");

        if(rDocument==null){

            Document insertObject = null;
            try {
                insertObject = MongoHelper.toMongoDocument(pSpeech);
                this.getCollection("speech").insertOne(insertObject);
                rDocument = getObject(pSpeech.getID(), "speech");

            } catch (Exception e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }

        }


        return rDocument;
    }

    public Document insertComment(Comment pComment)  {

        Document rDocument = null;

        rDocument = getObject(pComment.getID(), "comment");

        if(rDocument==null){

            Document insertObject = MongoHelper.toMongoDocument(pComment);

            this.getCollection("comment").insertOne(insertObject);
            rDocument = getObject(pComment.getID(), "comment");
        }


        return rDocument;
    }

    public Document insertSpeaker(Speaker pSpeaker)  {

        Document rDocument = null;

        rDocument = getObject(pSpeaker.getID(), "speaker");

        if(rDocument==null){

            Document insertObject = MongoHelper.toMongoDocument(pSpeaker);

            this.getCollection("speaker").insertOne(insertObject);
            rDocument = getObject(pSpeaker.getID(), "speaker");
        }
        else{
            Document updateObject = MongoHelper.toMongoDocument(pSpeaker);
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("_id", pSpeaker.getID());
            this.getCollection("speaker").replaceOne(whereQuery, updateObject);
        }


        return rDocument;
    }

    public String createIndex(String sCollection, String sField){

        BasicDBObject query = new BasicDBObject();
        query.put(sField, "text");
        String rString = this.getCollection(sCollection).createIndex(query);

        return rString;
    }
}
