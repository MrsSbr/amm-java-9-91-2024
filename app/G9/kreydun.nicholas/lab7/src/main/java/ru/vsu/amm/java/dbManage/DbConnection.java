package ru.vsu.amm.java.dbManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.vsu.amm.java.service.Logg.logger;

public class DbConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5434/";

    public Connection connectToDb(String dbname, String user, String pass) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL + dbname + "?user=" + user + "&password=" + pass);

            if (conn != null) {
                logger.info("Connected to PostgreSQL database: " + dbname);
            } else {
                logger.warning("Failed to connect to PostgreSQL database: " + dbname);
            }
        } catch (SQLException e) {
            logger.info("Error connecting to PostgreSQL: " + e.getMessage());
        }

        return conn;
    }
}