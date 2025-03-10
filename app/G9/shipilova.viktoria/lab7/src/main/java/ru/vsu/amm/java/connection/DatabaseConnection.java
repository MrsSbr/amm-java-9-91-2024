package ru.vsu.amm.java.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection getConnection() throws SQLException {
        String dbUrl = null;
        String dbUser = null;
        String dbPass = null;

        Properties props = new Properties();

        try(InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            props.load(input);

            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.username");
            dbPass = props.getProperty("db.password");
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load properties file", e);
            throw new SQLException("Could not read properties file");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not connect to database", e);
            throw new SQLException("Could not connect to database");
        }
        return connection;
    }
}
