package com.codebricks.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.FileInputStream;
import java.util.Properties;

public class DatabaseManager {

    /* STATE */
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    /* CONNECT */
    public static void connect() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("db.properties"));

            String uri  = props.getProperty("db.uri");
            String name = props.getProperty("db.name");

            mongoClient = MongoClients.create(uri);
            database    = mongoClient.getDatabase(name);


        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to MongoDB: " + e.getMessage());
        }
    }

    /* GET DATABASE */
    public static MongoDatabase getDatabase() {
        return database;
    }

    /* CLOSE */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();

        }
    }
}