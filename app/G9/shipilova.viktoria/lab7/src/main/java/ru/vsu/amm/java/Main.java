package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setLogin("Vasyaaa");
        user.setPassword("123");
        user.setPhoneNumber("+79202222224");
        user.setEmail("vasyaaa@gmail.com");
        UserRepository userRepository = new UserRepository();
        userRepository.save(user);
        var users = userRepository.getAll();
        for (User u: users) {
            System.out.println(u);
        }
    }
}