package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;

import javax.sql.DataSource;

public class RentalObjectRepository {

    private final DataSource dataSource;

    public RentalObjectRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }




}
