package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.SelectUserEntity;
import ru.vsu.amm.java.entities.UpdateUserEntity;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.services.UsersSQLQueriesGenerator;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            String sqlQuery = UsersSQLQueriesGenerator.createUser(new UserEntity(
                    0,
                    "Jack",
                    "123",
                    LocalDate.now()));
            System.out.println(sqlQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            String sqlQuery = UsersSQLQueriesGenerator.getUsers(new SelectUserEntity(
                    false,
                    false,
                    false,
                    false));
            System.out.println(sqlQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            String sqlQuery = UsersSQLQueriesGenerator.updateUser(42, new UpdateUserEntity(
                    "Jack",
                    "123",
                    LocalDate.now()));
            System.out.println(sqlQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            String  sqlQuery = UsersSQLQueriesGenerator.deleteUsers();
            System.out.println(sqlQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}