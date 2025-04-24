package ru.vsu.amm.java.configuration;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.repository.SessionRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.vsu.amm.java.utils.LoggerInitializer.initializeLogger;

public class DatabaseConfiguration {

    private static final Logger logger = initializeLogger(
            "app/G91/simonov.ivan/lab7/src/main/java/ru/vsu/amm/java/logs/database-configuration-logs.log",
            SessionRepository.class.getName());

    public static DataSource getMainDataSource() {

        return getDataSource(false);

    }

    public static DataSource getTestDataSource() {

        return getDataSource(true);

    }

    private static DataSource getDataSource(boolean isTest) {

        Properties properties = new Properties();

        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream(isTest ? "testdb.properties" : "database.properties")) {

            properties.load(input);

        } catch (IOException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUrl(properties.getProperty("db.url") + "?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUser(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));

        return dataSource;
    }
}
