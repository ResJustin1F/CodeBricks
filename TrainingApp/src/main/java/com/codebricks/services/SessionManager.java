package com.codebricks.services;

public class SessionManager {

    // STATE
    private static String currentEmail;

    //SET
    public static void set(String email) {
        currentEmail = email;
    }

    // GET
    public static String getEmail() {
        return currentEmail;
    }

    // CLEAR
    public static void clear() {
        currentEmail = null;
    }
}