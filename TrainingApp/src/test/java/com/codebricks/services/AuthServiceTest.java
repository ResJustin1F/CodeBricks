package com.codebricks.services;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    public void testPasswordHashNotPlainText() {
        String password = "mypassword123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        assertNotEquals(password, hash);
    }

    @Test
    public void testPasswordHashMatches() {
        String password = "mypassword123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        assertTrue(BCrypt.checkpw(password, hash));
    }

    @Test
    public void testWrongPasswordDoesNotMatch() {
        String password = "mypassword123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        assertFalse(BCrypt.checkpw("wrongpassword", hash));
    }
}