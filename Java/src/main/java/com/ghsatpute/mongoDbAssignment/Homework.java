package com.ghsatpute.mongoDbAssignment;


import com.mongodb.*;

import javax.xml.soap.SOAPPart;
import java.net.UnknownHostException;
import java.util.List;

/*
 *  @author : Ganesh Satpute
 *  @date   : 1/19/2015
 *
 */
public class Homework {

    public static void main(String[] args) throws  UnknownHostException {
         //Assignment_2_2();
        Assignment_3_1();
    }
    // This method solves the problem of MongoDb University online course
    // Homework problem 2.2
    /*
    * Write a program in the language of your choice that will remove the grade of type
    * "homework" with the lowest score for each student from the dataset that you imported
    * in HW 2.1. Since each document is one grade, it should remove one document per student.

    * Hint/spoiler: If you select homework grade-documents, sort by student and then by score,
    * you can iterate through and find the lowest score for each student by noticing a change
    * in student id. As you notice that change of student_id, remove the document.
    * */
    public static void Assignment_2_2() throws UnknownHostException {
        // Connect to client
        MongoClient client = new MongoClient();
        // Get the database
        DB db = client.getDB("students");
        // Get the collection
        DBCollection dbCollection = db.getCollection("grades");
        DBCursor cursor = dbCollection.find()
                .sort(new BasicDBObject("student_id", 1).append("type", 1).append("score", -1)); // Sort by student and score
        DBObject dbObject = null;
        while(cursor.hasNext())
        {
            if(dbObject == null) {
                dbObject = cursor.next();
                System.out.println("First object : " + dbObject.toString());
            }
                while(cursor.hasNext())
                {
                    // Get the next object
                    DBObject object = cursor.next(); // We have to skip this object
                    // So get another if any
                    if(cursor.hasNext())
                        object = cursor.next();

                    System.out.println("Next object is : " + object.toString());
                    // If the student is the same student
                    if(object.get("student_id").equals(dbObject.get("student_id"))) {
                        // If type is homework
                        if (object.get("type").toString().equalsIgnoreCase("homework")) {
                            // Remove that row
                            System.out.println("Removing " + object.toString());
                            dbCollection.remove(object);
                        }
                    }
                    else {
                        // As we have already sorted by student_id
                        // We break this loop
                        dbObject = object;
                        break;
                    }
                }
        }

    }

    // TODO: Write description
    public static void Assignment_3_1() throws UnknownHostException {
        // Create a client to connect to server
        MongoClient client = new MongoClient();
        // Connect to database
        DB db = client.getDB("school");
        // Connect to collection
        DBCollection collection = db.getCollection("students");

        // Create cursor to iterate through students
        DBCursor dbCursor = collection.find();

        while(dbCursor.hasNext()) {
            // Get the scores
            DBObject dbObject = dbCursor.next();

            // Remove the "homework" key-value pair with least score from document
            BasicDBList scores = (BasicDBList)dbObject.get("scores");

            // This will point to min score of homework
            DBObject min = null;
            // Iterate through each score object
            for(Object obj : scores) {
                DBObject temp = (DBObject)obj;
                // If object is of type homework
                if(temp.get("type").equals("homework")) {
                    // If this is the first element
                    if(min == null) {
                        min = temp;
                        //System.out.println("First time : " + temp);
                    }
                    // If minimum is less
                    else if ( (Double) temp.get("score")
                            < (Double) min.get("score")) {
                        min = temp;
                        //System.out.println("Minimum is : " + temp);
                    }
                }
            }
            if(min != null)
                scores.remove(min);

            // Update the document
            // Remember, here <code>dbObject<dbObject> is changed a bit, so you cannot pass it as it
            // So we are just passing just id
            collection.update(new BasicDBObject("_id", dbObject.get("_id")), new BasicDBObject("$set", new BasicDBObject("scores", scores)));
        }
    }
}
