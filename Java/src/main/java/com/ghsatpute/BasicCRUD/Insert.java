package com.ghsatpute.BasicCRUD;

import com.mongodb.*;
import com.sun.xml.internal.ws.api.client.SelectOptimalEncodingFeature;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by Ganesh on 1/18/2015.
 */
public class Insert {
    public static void main(String[] args) throws UnknownHostException {
        insert();

    }

    private static void insert() throws UnknownHostException {
        // Connect to client
        // If no parameters are passed, it takes default host and ports (?)
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("course");
        // This is the name of database (correctly collection) "insertTest"
        DBCollection collection = db.getCollection("insertTest");

        // Create Object to insert
        DBObject dbObject = new BasicDBObject().append("key1", "value1");
        System.out.println(dbObject);

        collection.insert(dbObject);
        // You cannot insert the same object again, it'll throw exception saying duplicate object
        // collection.insert(dbObject); <-- you cannot do this
        dbObject.removeField("_id");
        collection.insert(dbObject); // <-- now you can do this
        // This will print with <code>_id<code>
        // Need to check how the both in memory object and database document are in sync
        System.out.println(dbObject);

        // Inserting multiple document
        /*
        DBObject doc1 = new BasicDBObject().append("key1", "value2");
        DBObject doc2 = new BasicDBObject().append("key1", "value3");
        DBObject doc3 = new BasicDBObject().append("key1", "value4");
        collection.insert(Arrays.asList(doc1,doc2,doc3));
        System.out.println(doc3);
        */
    }

}
