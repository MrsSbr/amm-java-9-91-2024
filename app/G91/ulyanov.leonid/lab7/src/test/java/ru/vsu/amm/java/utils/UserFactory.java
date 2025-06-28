package ru.vsu.amm.java.utils;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.requests.Auth.RegisterRequest;

public class UserFactory {
    private static final int COST = 12;
    private static final BCryptUtil CRYPT = new BCryptUtil(COST);

    public static RegisterRequest correctRegisterRequest() {
        return new RegisterRequest(
                "adress1@mail.com",
                "password1234",
                "Lastname",
                "Firstname",
                "Patronymic",
                "780005553535"
        );
    }

    public static RegisterRequest wrongRegisterRequest() {
        return new RegisterRequest(
                "adress2@mail.com",
                "password1234 ",
                "Имя",
                "Firstname",
                "Patronymic",
                "79001002030"
        );
    }

    public static User newUser() {
        return new User(
                null,
                "adress1@mail.com",
                CRYPT.hashPassword("password1234"),
                "Lastname",
                "Firstname",
                "Patronymic",
                "780005553535"
        );
    }

    public static LoginRequest correctLoginRequest() {
        return new LoginRequest(
                "adress1@mail.com",
                "password1234"
        );
    }

    public static LoginRequest wrongLoginRequest() {
        return new LoginRequest(
                "adress1@mail.com",
                "otherpassword1234"
        );
    }
}