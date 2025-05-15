package ru.vsu.amm.java.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordEncoder {
    private final int cost;

    public BcryptPasswordEncoder(int cost) {
        this.cost=cost;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(cost));
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}