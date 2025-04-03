package ru.vsu.amm.java.exceptions;

public class WrongUserCredentialsException extends RuntimeException {
    public WrongUserCredentialsException(String message) {
        super(message);
    }
}