package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.config.DatabaseConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IntegrationTest {

    private DataSource ds;

    @BeforeAll
    void init() {
        ds = DatabaseConfiguration.getDataSource();
    }

    @AfterEach
    void cleanup() throws Exception {
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM booking");
            st.executeUpdate("DELETE FROM flight");
            st.executeUpdate("DELETE FROM client");
        }
    }
}
