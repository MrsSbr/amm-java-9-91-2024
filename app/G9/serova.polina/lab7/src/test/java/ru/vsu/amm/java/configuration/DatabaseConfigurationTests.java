package ru.vsu.amm.java.configuration;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

class DatabaseConfigurationTests {

    @Test
    void getDataSource_shouldReturnSameInstance() {
        DataSource firstInstance = DatabaseConfiguration.getDataSource();
        DataSource secondInstance = DatabaseConfiguration.getDataSource();
        assertSame(firstInstance, secondInstance,
                "Должен возвращаться один и тот же экземпляр DataSource");
    }

    @Test
    void getDataSource_shouldReturnWorkingDataSource() throws SQLException {
        DataSource dataSource = DatabaseConfiguration.getDataSource();
        assertNotNull(dataSource, "DataSource не должен быть null");

        try (var connection = dataSource.getConnection()) {
            assertNotNull(connection, "Должно возвращаться валидное соединение");
            assertTrue(connection.isValid(1), "Соединение должно быть рабочим");
        }
    }
}