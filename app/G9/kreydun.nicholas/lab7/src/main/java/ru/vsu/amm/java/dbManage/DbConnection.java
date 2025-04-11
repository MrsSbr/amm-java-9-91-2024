package ru.vsu.amm.java.dbManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.vsu.amm.java.services.Logg.logger;

public class DbConnection {
    private static final Properties properties = new Properties();

    static {
        // Загрузка файла db.properties
        try (FileInputStream fis = new FileInputStream("/Users/a123/IdeaProjects/amm-java-9-91-2024/app/G9/kreydun.nicholas/lab7/src/main/java/ru/vsu/amm/java/db/db.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            logger.warning("Error loading database properties file: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {

        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.warning("PostgreSQL JDBC driver not found.");
            throw new SQLException("PostgreSQL JDBC driver not found.", e);
        }

        return DriverManager.getConnection(url, username, password);
    }
}
