package ru.vsu.amm.java;

// Исключение для некорректных данных при вводе
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
