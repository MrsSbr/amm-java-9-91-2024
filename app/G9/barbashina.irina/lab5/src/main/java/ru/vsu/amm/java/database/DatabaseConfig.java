package ru.vsu.amm.java.database;

import ru.vsu.amm.java.annotation.Config;
import ru.vsu.amm.java.annotation.Property;

@Config(path = "config/db.properties")
public interface DatabaseConfig {
    @Property("db.jdbcUrl")
    String getJdbcUrl();

    @Property("db.user")
    String getUser();

    @Property("db.password")
    String getPassword();

    @Property("db.ssl")
    boolean getSsl();
}
