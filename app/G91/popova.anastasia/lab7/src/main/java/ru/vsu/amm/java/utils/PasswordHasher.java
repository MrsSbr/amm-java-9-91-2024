package ru.vsu.amm.java.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public  static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPasswordHash(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
