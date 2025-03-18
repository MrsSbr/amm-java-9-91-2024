package ru.vsu.amm.java.dbConnection;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.entities.EmployeeEntity;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {
    public static DataSource getDataSource() {
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(prop.getProperty("db.url"));
        dataSource.setUser(prop.getProperty("db.user"));
        dataSource.setPassword(prop.getProperty("db.password"));
        return dataSource;
    }
}
