package ru.vsu.amm.java.entity_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testCreateUser() {
        User user = new User();

        assertNotNull(user.getUserID());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }

    @Test
    public void testUserSettersAndGetters() {
        User user = new User();

        String testName = "Test User";
        String testEmail = "email@example.com";
        String testPassword = "password123";

        user.setName(testName);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

        assertEquals(testName, user.getName());
        assertEquals(testEmail, user.getEmail());
        assertEquals(testPassword, user.getPassword());
    }

}
