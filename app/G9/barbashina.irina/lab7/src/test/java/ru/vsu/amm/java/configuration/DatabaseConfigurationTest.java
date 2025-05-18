package ru.vsu.amm.java.configuration;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


class DatabaseConfigurationTest {

    @Test
    void getDataSourceShouldReturnValidDataSource() {
        DataSource dataSource = DatabaseConfiguration.getDataSource();
        assertNotNull(dataSource);

        try {
            assertNotNull(dataSource.getConnection());
        } catch (SQLException e) {
            fail("Should be able to get connection from datasource");
        }
    }
}