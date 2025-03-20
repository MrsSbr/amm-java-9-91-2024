package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

        // Создание нового пользователя
        User newUser = new User();
        newUser.setUsername("testUser1234");
        newUser.setEmail("testUser123@example.com");
        newUser.setPassword("password");

        UUID userId = userRepository.create(newUser);
        if (userId != null) {
            System.out.println("User created with ID: " + userId);

            // Получение и вывод данных о пользователе
            User retrievedUser = userRepository.getById(userId);
            if (retrievedUser != null) {
                System.out.println("Retrieved User: " + retrievedUser.getUsername());

                // Обновление пользователя
                retrievedUser.setUsername("updatedUser123");
                boolean isUpdated = userRepository.update(retrievedUser);
                if (isUpdated) {
                    User updatedUser = userRepository.getById(userId);
                    System.out.println("Updated User: " + updatedUser.getUsername());
                }
            }
        }

        List<User> allUsers = userRepository.getAll();
        System.out.println("Total Users: " + allUsers.size());
        allUsers.forEach(user -> System.out.println("User: " + user.getUsername()));
    }
}