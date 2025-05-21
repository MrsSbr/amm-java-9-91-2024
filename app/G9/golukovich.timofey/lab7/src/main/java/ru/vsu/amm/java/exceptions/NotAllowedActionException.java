package ru.vsu.amm.java.exceptions;

public class NotAllowedActionException extends RuntimeException {
    public NotAllowedActionException(String message) {
        super(message);
    }
}
