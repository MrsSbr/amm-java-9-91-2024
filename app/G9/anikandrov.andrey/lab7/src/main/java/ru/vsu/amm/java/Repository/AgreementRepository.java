package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;

import javax.sql.DataSource;

public class AgreementRepository {

    private final DataSource dataSource;

    public AgreementRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }




}
