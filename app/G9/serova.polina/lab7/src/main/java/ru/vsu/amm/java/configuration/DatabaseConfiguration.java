package ru.vsu.amm.java.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfiguration {
    private static final class Holder {
        static final DatabaseConfiguration INSTANCE = new DatabaseConfiguration();
    }

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
    private final HikariDataSource dataSource;

    private DatabaseConfiguration() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(org.postgresql.Driver.class.getName());
        DatabaseProperties databaseProperties = new DatabaseProperties("db.properties");
        hikariDataSource.setJdbcUrl(databaseProperties.getUrl());
        hikariDataSource.setUsername(databaseProperties.getUsername());
        hikariDataSource.setPassword(databaseProperties.getPassword());
        executeInitScript(hikariDataSource, databaseProperties.getInitScriptPath());
        dataSource = hikariDataSource;
    }

    public static DataSource getDataSource() {
        return Holder.INSTANCE.dataSource;
    }

    private void executeInitScript(HikariDataSource ds, String scriptPath) {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             InputStream is = DatabaseConfiguration.class.getClassLoader()
                     .getResourceAsStream(scriptPath)) {

            if (is == null) {
                throw new IOException("Не удалось прочитать файл " + scriptPath);
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String[] sqlCommands = sql.split(";\\s*");
            for (String command : sqlCommands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command);
                }
            }

        } catch (SQLException | IOException e) {
            log.error("Ошибка при инициализации баз данных", e);
        }
    }
}
