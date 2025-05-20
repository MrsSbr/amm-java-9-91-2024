package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.UserRole;
import ru.vsu.amm.java.model.requests.UserRequest;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;

public class UserTestData {

    public static final String USERNAME_ADMIN = "admin";
    public static final String USERNAME_USER = "user";
    public static final String USERNAME_NEW = "newuser";
    public static final String USERNAME_EXISTING = "existing";
    public static final String USERNAME_MISSING = "missing";
    public static final String USERNAME_GHOST = "Ghost";
    public static final String USERNAME_CAROL = "Carol";
    public static final String USERNAME_DAVE = "Dave";
    public static final String USERNAME_BOB = "Bob";
    public static final String USERNAME_ALICE = "Alice";

    public static final String PASSWORD_CORRECT = "secure";
    public static final String PASSWORD_WRONG = "wrongpass";
    public static final String PASSWORD_HASHED = "hashed";
    public static final String PASSWORD_NEW = "pass";
    public static final String PASSWORD_BOB = "12345";
    public static final String PASSWORD_ALICE = "securePass";
    public static final String PASSWORD_CAROL = "mySecret";
    public static final String PASSWORD_DAVE = "correct";
    public static final String PASSWORD_GHOST = "noSuchUser";

    private static final BcryptPasswordEncoder encoder = new BcryptPasswordEncoder(12);

    public static UserEntity adminEntity() {
        return new UserEntity(1L, USERNAME_ADMIN, PASSWORD_HASHED, UserRole.ADMIN);
    }

    public static UserEntity userEntity() {
        return new UserEntity(2L, USERNAME_USER, PASSWORD_HASHED, UserRole.USER);
    }

    public static UserEntity existingUserEntity() {
        return new UserEntity(3L, USERNAME_EXISTING, PASSWORD_NEW, UserRole.USER);
    }

    public static UserEntity bobEntity() {
        return new UserEntity(null, USERNAME_BOB, encoder.hashPassword(PASSWORD_BOB), UserRole.USER);
    }

    public static UserEntity aliceEntity() {
        return new UserEntity(null, USERNAME_ALICE, encoder.hashPassword(PASSWORD_ALICE), UserRole.USER);
    }

    public static UserEntity carolEntity() {
        return new UserEntity(null, USERNAME_CAROL, encoder.hashPassword(PASSWORD_CAROL), UserRole.ADMIN);
    }

    public static UserEntity daveEntity() {
        return new UserEntity(null, USERNAME_DAVE, encoder.hashPassword(PASSWORD_DAVE), UserRole.USER);
    }

    public static UserRequest userRequest(String username, String password) {
        return new UserRequest(username, password);
    }

    public static UserRequest adminRequest() {
        return new UserRequest(USERNAME_ADMIN, PASSWORD_CORRECT);
    }

    public static UserRequest newUserRequest() {
        return new UserRequest(USERNAME_NEW, PASSWORD_NEW);
    }

    public static UserRequest existingUserRequest() {
        return new UserRequest(USERNAME_EXISTING, PASSWORD_NEW);
    }

    public static UserRequest bobRequest() {
        return new UserRequest(USERNAME_BOB, PASSWORD_BOB);
    }

    public static UserRequest aliceRequest() {
        return new UserRequest(USERNAME_ALICE, PASSWORD_ALICE);
    }

    public static UserRequest carolRequest() {
        return new UserRequest(USERNAME_CAROL, PASSWORD_CAROL);
    }

    public static UserRequest daveCorrectRequest() {
        return new UserRequest(USERNAME_DAVE, PASSWORD_DAVE);
    }

    public static UserRequest daveWrongRequest() {
        return new UserRequest(USERNAME_DAVE, PASSWORD_WRONG);
    }

    public static UserRequest ghostRequest() {
        return new UserRequest(USERNAME_GHOST, PASSWORD_GHOST);
    }
}
