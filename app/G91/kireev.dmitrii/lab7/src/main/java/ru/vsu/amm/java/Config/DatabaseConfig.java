package ru.vsu.amm.java.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.vsu.amm.java.Model.DTO.PropertiesDTO;
import ru.vsu.amm.java.Util.PropertiesInitializer;

import javax.sql.DataSource;


public class DatabaseConfig {

    private static volatile HikariDataSource dataSource;

    private DatabaseConfig() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DatabaseConfig.class) {
                if (dataSource == null) {

                    PropertiesDTO properties = PropertiesInitializer.loadProperties();
                    
                    HikariConfig config = getHikariConfig(properties);
                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }


    private static HikariConfig getHikariConfig(PropertiesDTO properties) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.url());
        config.setUsername(properties.username());
        config.setPassword(properties.password());

        config.setMaximumPoolSize(10); // Максимальное количество соединений
        config.setMinimumIdle(2);     // Минимальное количество соединений в режиме простоя
        config.setIdleTimeout(30000);  // Время простоя соединения перед закрытием (в миллисекундах)
        return config;
    }

}
