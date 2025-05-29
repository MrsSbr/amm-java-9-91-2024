package ru.vsu.amm.java.exceptions;

public class TaskStatusDoesNotExistException extends RuntimeException {
    public TaskStatusDoesNotExistException(String message) {
        super(message);
    }
}
