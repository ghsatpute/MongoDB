package com.ghsatpute.BasicCRUD;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/*
 *  @author : Ganesh Satpute
 *  @date   : 1/18/2015
 *
 */
public class Update {

    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createDbCollection();
        // Now update the some data
        System.out.println("data" + collection.findOne(new BasicDBObject("_id",1)));
        collection.update(new BasicDBObject("_id", 1), new BasicDBObject("data", "new data"));
        System.out.println("new data" + collection.findOne(new BasicDBObject("_id",1)));

    }

    private static DBCollection createDbCollection() throws UnknownHostException{
        MongoClient client = new MongoClient();
        DB db = client.getDB("Update");
        DBCollection collection = db.getCollection("test");
        collection.drop();
        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject().append("_id", i).append("data", "item_"+i));
        }
        return collection;
    }
}
