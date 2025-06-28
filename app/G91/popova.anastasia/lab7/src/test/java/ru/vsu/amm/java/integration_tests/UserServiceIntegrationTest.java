package ru.vsu.amm.java.integration_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.UserService;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntegrationTest extends BaseIntegrationTest {

    private UserService createUserService() {
        return new UserService(new UserRepository());
    }

    @Test
    void registerTest() {
        UserService service = createUserService();
        User user = service.register("testuser", "test@example.com", "password123");

        assertNotNull(user.getUserID());
        assertEquals("testuser", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertNotEquals("password123", user.getPassword()); // password should be hashed
    }

    @Test
    void loginTest() {
        UserService service = createUserService();
        User registered = service.register("loginuser", "login@example.com", "password123");

        User loggedIn = service.login("login@example.com", "password123");

        assertEquals(registered.getUserID(), loggedIn.getUserID());
    }

    @Test
    void getByIDTest() {
        UserService service = createUserService();
        User registered = service.register("getbyid", "getbyid@example.com", "password123");

        User found = service.getByID(registered.getUserID());

        assertEquals(registered.getUserID(), found.getUserID());
    }

    @Test
    void updateUserNameTest() {
        UserService service = createUserService();
        User user = service.register("oldname", "update@example.com", "password123");

        service.updateUserName(user.getUserID(), "newname");
        User updated = service.getByID(user.getUserID());

        assertEquals("newname", updated.getName());
    }

    @Test
    void deleteUserTest() {
        UserService service = createUserService();
        User user = service.register("todelete", "delete@example.com", "password123");

        service.deleteUser(user.getUserID());

        assertThrows(IllegalArgumentException.class, () -> service.getByID(user.getUserID()));
    }
}