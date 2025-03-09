package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;

import javax.sql.DataSource;

public class UserRepository {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }




}
