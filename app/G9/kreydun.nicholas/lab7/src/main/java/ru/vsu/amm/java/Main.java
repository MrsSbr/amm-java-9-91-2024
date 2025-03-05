package ru.vsu.amm.java;

import ru.vsu.amm.java.dbManage.DbManager;
import ru.vsu.amm.java.dbManage.DbConnection;
import ru.vsu.amm.java.entities.User;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        DbConnection db = new DbConnection();
        db.connectToDb("SocialNetwork_", "postgres", "12345");

        DbManager dbManager = new DbManager();

        // Пример использования User
        User newUser = new User();
        newUser.setUsername("testUsefdsfdr5");
        newUser.setEmail("t3gfdgdesfsft@example.com");
        newUser.setPassword("password");
        UUID userId = dbManager.createUser(newUser);

        if (userId != null) {
            User retrievedUser = dbManager.getUserById(userId);
            System.out.println("Retrieved User: " + retrievedUser.getUsername());

            retrievedUser.setUsername("updafdfdsfgfgdtedUser2");
            dbManager.updateUser(retrievedUser);

            User updatedUser = dbManager.getUserById(userId);
            System.out.println("Updated User: " + updatedUser.getUsername());
        }

        List<User> allUsers = dbManager.getAllUsers();
        System.out.println("All Users: " + allUsers.size());

    }
}