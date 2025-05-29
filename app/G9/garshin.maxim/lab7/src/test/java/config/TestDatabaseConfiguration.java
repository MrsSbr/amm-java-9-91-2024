package config;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDatabaseConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(TestDatabaseConfiguration.class);

    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties properties = new Properties();
        try (InputStream inputStream = TestDatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("test.properties")) {
            if (inputStream == null) {
                logger.error("Unable to find application.properties");
                throw new RuntimeException("application.properties not found");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load properties from application.properties: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        pgSimpleDataSource.setUrl(properties.getProperty("db.url"));
        pgSimpleDataSource.setUser(properties.getProperty("db.username"));
        pgSimpleDataSource.setPassword(properties.getProperty("db.password"));

        logger.info("Configuring DataSource with URL: {}", properties.getProperty("db.url"));
        logger.info("Using username: {}", properties.getProperty("db.username"));

        return pgSimpleDataSource;
    }
}