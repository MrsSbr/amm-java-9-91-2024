package ru.vsu.amm.java.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseConfigurationIntegrationTests {

    private static DataSource dataSource;

    @BeforeAll
    static void setup() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @AfterAll
    static void cleanup() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }

    @Test
    void shouldCreateUserEntityTable() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'user_entity')")) {

            assertTrue(rs.next(), "Должен возвращаться результат запроса");
            assertTrue(rs.getBoolean(1), "Таблица user_entity должна существовать");
        }
    }

    @Test
    void shouldCreatePlanEntityTable() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'plan_entity')")) {

            assertTrue(rs.next(), "Должен возвращаться результат запроса");
            assertTrue(rs.getBoolean(1), "Таблица plan_entity должна существовать");
        }
    }

    @Test
    void shouldCreateForeignKeys() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT COUNT(*) FROM information_schema.table_constraints " +
                             "WHERE table_name = 'plan_entity' AND constraint_type = 'FOREIGN KEY'")) {

            assertTrue(rs.next(), "Должен возвращаться результат запроса");
            assertEquals(2, rs.getInt(1), "Должно быть 2 внешних ключа в plan_entity");
        }
    }
}