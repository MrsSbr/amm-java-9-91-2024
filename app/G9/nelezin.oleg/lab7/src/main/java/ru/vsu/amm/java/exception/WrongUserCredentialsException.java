package ru.vsu.amm.java.exception;

public class WrongUserCredentialsException extends RuntimeException {
    public WrongUserCredentialsException(String message) {
        super(message);
    }
}
