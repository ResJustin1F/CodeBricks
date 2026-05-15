package com.codebricks.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {

    @Test
    public void testSetAndGetEmail() {
        SessionManager.set("test@example.com");
        assertEquals("test@example.com", SessionManager.getEmail());
    }

    @Test
    public void testClearSession() {
        SessionManager.set("test@example.com");
        SessionManager.clear();
        assertNull(SessionManager.getEmail());
    }

    @Test
    public void testOverwriteSession() {
        SessionManager.set("first@example.com");
        SessionManager.set("second@example.com");
        assertEquals("second@example.com", SessionManager.getEmail());
    }
}