package ru.vsu.amm.java.exception;

public class DbException extends RuntimeException {
    public DbException(String message) {
        super(message);
    }
}
