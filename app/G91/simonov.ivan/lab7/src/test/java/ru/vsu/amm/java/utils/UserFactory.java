package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.requests.RegisterRequest;
import ru.vsu.amm.java.requests.SignInRequest;

import java.util.List;

public class UserFactory {

    public static User makeUser() {

        return new User(
                "I",
                "I",
                "I",
                "i@i.ii",
                "1",
                Role.USER
        );

    }

    public static RegisterRequest makeRegisterRequest() {

        return new RegisterRequest(
                "I",
                "I",
                "I",
                "i@i.ii",
                "1",
                Role.USER);

    }

    public static SignInRequest makeSignInRequest() {

        return new SignInRequest(
                "i@i.ii",
                "1");

    }

    public static List<User> makeFewUsers() {

        return List.of(
                new User(
                        "I",
                        "I",
                        "I",
                        "i@i.ii",
                        "1",
                        Role.USER),

                new User(
                        "K",
                        "K",
                        "K",
                        "k@k.kk",
                        "2",
                        Role.ADMIN),

                new User(
                        "U",
                        "U",
                        "U",
                        "u@u.uu",
                        "3",
                        Role.EMPLOYEE)

        );

    }

}
