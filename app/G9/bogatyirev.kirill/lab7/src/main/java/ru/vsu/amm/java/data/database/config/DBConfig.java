package ru.vsu.amm.java.data.database.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    public static DataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        Properties properties = new Properties();

        try(InputStream in = DBConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            if(in == null) {
                throw new IllegalStateException("db.properties not found");
            }

            properties.load(in);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            if(url == null || user == null || password == null) {
                throw new IllegalStateException("db.properties have no properties");
            }

            dataSource.setUrl(url);
            dataSource.setUser(user);
            dataSource.setPassword(password);

        } catch(IOException e) {
            throw new RuntimeException("db.properties load fail",e);
        }

        return dataSource;
    }
}
