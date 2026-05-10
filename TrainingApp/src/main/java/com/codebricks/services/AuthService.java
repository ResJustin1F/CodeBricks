package com.codebricks.services;

public class AuthService {

    // TODO: initialize MongoDB connection here
    // MongoClient mongoClient;
    // MongoDatabase database;

    /**
     * Registers a new user with email and hashed password
     * @return true if successful, false if email already exists
     */
    public boolean register(String email, String password) {
        // TODO: implement when MongoDB is connected
        // 1. Check if email already exists in Users collection
        // 2. Hash the password using BCrypt
        // 3. Insert new user document into MongoDB
        return false;
    }

    /**
     * Validates user credentials against MongoDB
     * @return true if credentials are valid
     */
    public boolean login(String email, String password) {
        // TODO: implement when MongoDB is connected
        // 1. Find user by email in Users collection
        // 2. Compare hashed password using BCrypt
        // 3. Return result
        return false;
    }
}