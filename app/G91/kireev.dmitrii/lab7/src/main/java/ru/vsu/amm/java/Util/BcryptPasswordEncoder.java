package ru.vsu.amm.java.Util;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordEncoder {

    private final int COST = 12;

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
