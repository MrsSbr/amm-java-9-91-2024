package ru.vsu.amm.java.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    private PasswordEncoder() {
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }
}
