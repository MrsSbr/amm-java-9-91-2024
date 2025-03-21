package ru.vsu.amm.java.dbManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.vsu.amm.java.services.Logg.logger;

import java.util.Properties;

public class DbConnection {
    private static final Properties properties = new Properties();

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            logger.warning("PostgreSQL JDBC driver not found.");
            throw new SQLException("PostgreSQL JDBC driver not found.", e);
        }
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/SocialNetwork_",
                "postgres",
                "12345");
    };
}
