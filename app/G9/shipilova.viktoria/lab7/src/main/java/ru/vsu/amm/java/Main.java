package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setLogin("Petya");
        user.setPassword("111");
        user.setPhoneNumber("+79202222227");
        user.setEmail("petya@gmail.com");
        UserRepository userRepository = new UserRepository();
        userRepository.save(user);
        var users = userRepository.getAll();
        for (User u: users) {
            System.out.println(u);
        }
    }
}