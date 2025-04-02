package ru.vsu.amm.java.Exception;

import java.io.IOException;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }
}
