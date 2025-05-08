package ru.vsu.amm.java.exceptions;

public class PostDoesNotExistException extends RuntimeException {
    public PostDoesNotExistException(String message) {
        super(message);
    }
}