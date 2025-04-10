package ru.vsu.amm.java.Exeption;

public class UnCorrectDataException extends RuntimeException {
    public UnCorrectDataException(String msg) {
        super(msg);
    }
}
