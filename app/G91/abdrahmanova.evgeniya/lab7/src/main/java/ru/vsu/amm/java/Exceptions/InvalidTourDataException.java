package ru.vsu.amm.java.Exceptions;

public class InvalidTourDataException extends RuntimeException {
    public InvalidTourDataException(String message) {
        super(message);
    }
}