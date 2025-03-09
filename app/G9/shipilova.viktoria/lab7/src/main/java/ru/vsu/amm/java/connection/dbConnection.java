package ru.vsu.amm.java.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbConnection {

    public static Connection getConnection() throws SQLException {
        String dbUrl = null;
        String dbUser = null;
        String dbPass = null;

        FileInputStream fis;
        Properties props = new Properties();

        try {
            fis = new FileInputStream("src/main/java/ru/vsu/amm/java/resources/db.properties");
            props.load(fis);

            dbUrl = props.getProperty("db.uel");
            dbUser = props.getProperty("db.username");
            dbPass = props.getProperty("db.password");
        } catch (FileNotFoundException e) {
            throw new SQLException("Could not find properties file");
        }
        catch (IOException e) {
            throw new SQLException("Could not read properties file");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            throw new SQLException("Could not connect to database");
        }
        return connection;
    }
}
