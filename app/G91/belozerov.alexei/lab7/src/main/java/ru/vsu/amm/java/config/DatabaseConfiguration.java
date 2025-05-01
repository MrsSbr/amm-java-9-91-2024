package ru.vsu.amm.java.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfiguration {

    private static final Logger log = Logger.getLogger(DatabaseConfiguration.class.getName());

    public static DataSource getDataSource() {
        Properties prop = new Properties();
        InputStream input = DatabaseConfiguration.class
                .getClassLoader()
                .getResourceAsStream("database.properties");
        if (input == null) {
            String msg = "The file 'database.properties' was not found in the classpath";
            log.severe(msg);
            throw new IllegalStateException(msg);
        }

        try (InputStream in = input) {
            prop.load(in);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to load database.properties", e);
            throw new IllegalStateException("Could not load database.properties", e);
        }

        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(prop.getProperty("db.url"));
        ds.setUser(prop.getProperty("db.user"));
        ds.setPassword(prop.getProperty("db.password"));
        log.info("DataSource configured: url=" + ds.getUrl() + ", user=" + ds.getUser());
        return ds;
    }
}
