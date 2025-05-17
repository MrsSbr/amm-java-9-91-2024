package utils;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;

public class TestDataFactory {
    private static final BcryptPasswordEncoder ENC = new BcryptPasswordEncoder();

    public static RegisterRequest userRegister() {
        return new RegisterRequest(
                "User",
                "user@mail.com",
                "pass123",
                Role.USER
        );
    }

    public static UserEntity userEntity() {
        return new UserEntity(
                null,
                "User",
                ENC.hashPassword("pass123"),
                "user@mail.com",
                Role.USER
        );
    }

    public static UserEntity adminEntity() {
        return new UserEntity(
                null,
                "Admin",
                ENC.hashPassword("admin"),
                "admin@mail.com",
                Role.ADMIN
        );
    }

    public static LoginRequest correctLoginRequest() {
        return new LoginRequest(
                "admin@mail.com",
                "admin"
        );
    }

    public static LoginRequest wrongLoginRequest() {
        return new LoginRequest(
                "admin@mail.com",
                "wrongpass"
        );
    }
}
