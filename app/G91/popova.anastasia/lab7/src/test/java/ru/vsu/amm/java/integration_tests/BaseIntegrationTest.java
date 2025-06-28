package ru.vsu.amm.java.integration_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.DB_config.DatabaseConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIntegrationTest {

    private DataSource ds;

    @BeforeAll
    void init() {
        ds = DatabaseConnection.getDataSource();
    }

    @AfterEach
    void cleanup() throws Exception {
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement()) {
            st.execute("SET CONSTRAINTS ALL DEFERRED");

            st.executeUpdate("DELETE FROM tasks");
            st.executeUpdate("DELETE FROM columns");
            st.executeUpdate("DELETE FROM boards");
            st.executeUpdate("DELETE FROM users");

            st.execute("SET CONSTRAINTS ALL IMMEDIATE");
        }
    }
}