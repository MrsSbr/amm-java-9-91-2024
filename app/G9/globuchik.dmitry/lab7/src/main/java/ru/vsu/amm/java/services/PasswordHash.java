package ru.vsu.amm.java.services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHash {
    private final int ITERATION_COUNT = 65536;
    private final int KEY_LENGTH = 256;
    private final int SALTSIZE = 16;
    private final String ALGORITHM = "PBKDF2WithHmacSHA1";

    public PasswordHash() {
    }

    public final byte[][] encrypt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALTSIZE];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);

        byte[] hash = keyFactory.generateSecret(spec).getEncoded();

        return new byte[][]{hash, salt};
    }

    public final byte[] encrypt(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);

        return keyFactory.generateSecret(spec).getEncoded();
    }
}
