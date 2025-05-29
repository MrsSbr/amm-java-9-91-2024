package ru.vsu.amm.java.data.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncodeService {

    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
