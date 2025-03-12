package ru.vsu.amm.java;

import ru.vsu.amm.java.database.DatabaseConfig;
import ru.vsu.amm.java.service.ConfigService;

public class Main {
    public static void main(String[] args) {
       try {
           DatabaseConfig config = ConfigService.load(DatabaseConfig.class);
           String jdbcUrl = config.getJdbcUrl();
           String user = config.getUser();
           String password = config.getPassword();
           boolean ssl = config.getSsl();

           System.out.println("JDBC URL: " + jdbcUrl);
           System.out.println("User: " + user);
           System.out.println("Password: " + password);
           System.out.println("SSL: " + ssl);
       } catch (IllegalArgumentException e) {
           System.err.println("Error: " + e.getMessage());
       }
    }
}