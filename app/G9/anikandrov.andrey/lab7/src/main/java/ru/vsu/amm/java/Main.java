package ru.vsu.amm.java;

import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Enums.Roles;
import ru.vsu.amm.java.Repository.UserRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
        UserEntity user = new UserEntity();
        user.setUserID(101L);
        user.setUserName("Andrey");
        user.setUserPassword("p@s5w0rd");
        user.setUserRole(Roles.ADMIN);
        user.setPhone("88005553535");
        user.setBirthDate(LocalDate.of(2004, Month.JULY, 4));

        UserRepository userRepository = new UserRepository();
        try {

            userRepository.save(user);
            var users = userRepository.findAll();
            for (UserEntity tmpUser: users) {
                System.out.println(tmpUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
