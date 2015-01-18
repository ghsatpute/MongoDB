package com.ghsatpute.BasicCRUD;

/*
 *  @author : Ganesh Satpute
 *  @date   : 1/18/2015
 *
 */

import com.mongodb.*;
import javafx.beans.property.SimpleObjectProperty;

import java.net.UnknownHostException;
import java.util.Random;

public class Retrieve {
    public static void main(String[] args) throws UnknownHostException {
        //retrieve();
        retrieveWithCondition();
    }

    private static void retrieveWithCondition() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");

        DBCollection collection = db.getCollection("TestRetrieve");
        // Drop the collection so if any data previously added is deleted
        collection.drop();

        // Add some random data, because you have just deleted it
        for (int i = 0 ; i < 10 ; i++) {
            // Range of x is 0-1
            // Range of y is 0-99
            collection.insert(new BasicDBObject().append("x", (int) (Math.random() * 2)).append("y", (int) (Math.random() * 100)));
        }
        // Create of DBObject
        // This object will work as your query
        // I want to find all documents who has x value 1
        DBObject query = new BasicDBObject().append("x",1);

        DBObject returnValue = collection.findOne(query);
        System.out.println(returnValue);
        // On similar basis all values can be returned with the help of cursor


        // On the similar lines, let's try little bit complex query
        query = new BasicDBObject().append("x",1) // x is equal to y
                .append("y", new BasicDBObject("$gt", 60)); // y is greater than 60
        // This is equivalent to JSON object and query
        /*
            {
             "x" : 1,
             "y" : { $gt : 60}
            }
         */
        returnValue = collection.findOne(query);
        System.out.println("Value with x = 1 and y greater than 60");
        System.out.println(returnValue);

        // Another way to do this is query builder
        QueryBuilder queryBuilder = new QueryBuilder().start("x").is(1)
                .and("y").greaterThan(60);
        returnValue = collection.findOne(queryBuilder.get(), // queryBuilder.get() returns DBObject
                new BasicDBObject("x", false));              // just for fun, Don't return x this time
        System.out.println("Value with x = 1 and y greater than 60 with QueryBuilder");
        System.out.println(returnValue);
    }

    private static void retrieve() throws UnknownHostException {
        // Create MongoDBClient to connect to database
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("course");

        // Connect to collection / table
        DBCollection collection = db.getCollection("retrieveTest");

        // Insert dummy data
        /* No need to do every time so commenting
        for(int i = 0 ; i < 10 ; i++) {
            collection.insert(new BasicDBObject().append("objectNumber", i));
        }
        */

        // Retrieve one document using findOne
        DBObject document = collection.findOne();
        System.out.println(document);

        // Retrieve multiple documents using find()
        DBCursor cursor = collection.find();
        while(cursor.hasNext()) {
            DBObject doc = cursor.next();
            System.out.println(doc);
        }
        // Retrieve the number of documents in the collection
        System.out.println("Number of documents in the collection : " + collection.count());
    }
}
