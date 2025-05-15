package configuration;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.configuration.DbConfiguration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDbConfiguration {
    public static DataSource getDataSource() {
        var pgSimpleDataSource = new PGSimpleDataSource();
        var properties = new Properties();
        try (InputStream input = DbConfiguration.class.getClassLoader()
                .getResourceAsStream("testDb.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        pgSimpleDataSource.setUrl(properties.getProperty("db.url"));
        pgSimpleDataSource.setUser(properties.getProperty("db.username"));
        pgSimpleDataSource.setPassword(properties.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}
