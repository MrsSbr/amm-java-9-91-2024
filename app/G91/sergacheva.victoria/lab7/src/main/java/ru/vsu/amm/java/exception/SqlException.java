package ru.vsu.amm.java.exception;

public class SqlException extends RuntimeException {
    public SqlException(String message) {
        super(message);
    }
}
