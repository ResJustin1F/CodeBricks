package com.codebricks.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class AuthService {

    /**
     * Registers a new user with email and hashed password
     * @return true if successful, false if email already exists
     */

    public boolean register(String email, String password) {
        MongoCollection<Document> users = DatabaseManager.getDatabase().getCollection("Users");

    //  CHECK IF EMAIL EXISTS
    if (users.find(Filters.eq("email", email)).first() != null) {
        return false;
    }

    //  HASH PASSWORD
    String hash = BCrypt.hashpw(password, BCrypt.gensalt());

    // INSERT USER
        Document user = new Document("email", email)
                .append("passwordHash", hash)
                .append("createdAt", LocalDateTime.now().toString());

        users.insertOne(user);
        return true;
    }

    /**
     * Validates user credentials against MongoDB
     * @return true if credentials are valid
     */

    public boolean login(String email, String password) {
        MongoCollection<Document> users = DatabaseManager.getDatabase().getCollection("Users");

        //FIND USER
        Document user = users.find(Filters.eq("email", email)).first();

        if (user == null) {
            return false;
        }

        //VERIFY PASSWORD
        String storedHash = user.getString("passwordHash");
        return BCrypt.checkpw(password, storedHash);
    }
}