package ru.vsu.amm.java.configuration;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class DatabasePropertiesTests {

    @Test
    void constructor_shouldLoadPropertiesSuccessfully() {
        try (MockedStatic<LoggerFactory> mockedLogger = Mockito.mockStatic(LoggerFactory.class)) {
            Logger mockLogger = mock(Logger.class);
            mockedLogger.when(() -> LoggerFactory.getLogger(DatabaseProperties.class))
                    .thenReturn(mockLogger);

                DatabaseProperties properties = new DatabaseProperties("db.properties");

                assertEquals("jdbc:postgresql://localhost:5432/postgres_test", properties.getUrl());
                assertEquals("postgres", properties.getUsername());
                assertEquals("test", properties.getPassword());
                assertEquals("db/CREATE.sql", properties.getInitScriptPath());
            }
    }

    @Test
    void constructor_shouldThrowExceptionWhenFileNotFound() {
        try (MockedStatic<LoggerFactory> mockedLogger = Mockito.mockStatic(LoggerFactory.class)) {
            Logger mockLogger = mock(Logger.class);
            mockedLogger.when(() -> LoggerFactory.getLogger(DatabaseProperties.class))
                    .thenReturn(mockLogger);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> new DatabaseProperties("nonexistent.properties"),
                    "Должно выбрасываться исключение при отсутствии файла");

            assertEquals("Файл конфигурации nonexistent.properties не найден", exception.getMessage());
        }
    }
}
