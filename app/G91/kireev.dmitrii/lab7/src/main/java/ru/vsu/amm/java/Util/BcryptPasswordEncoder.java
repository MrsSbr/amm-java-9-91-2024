package ru.vsu.amm.java.Util;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordEncoder {

    private static final int COST = 12;

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
