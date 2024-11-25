package ru.vsu.amm.java;

import ru.vsu.amm.java.builder.SQLQueryBuilder;
import ru.vsu.amm.java.entity.MyEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryApp {
    private static final Logger logger = Logger.getLogger(QueryApp.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting SQL Query Builder example");

        MyEntity entity = new MyEntity();
        entity.setAge(20);
        entity.setName("Vika");
        entity.setId(1);

        SQLQueryBuilder queryBuilder = new SQLQueryBuilder(MyEntity.class);

        try {
            System.out.println(queryBuilder.generateInsertQuery(entity));
            logger.log(Level.INFO, "Generate INSERT query");
            System.out.println(queryBuilder.generateSelectQuery("id = 1"));
            logger.log(Level.INFO, "Generate SELECT query");
            System.out.println(queryBuilder.generateUpdateQuery(entity, "id = 1"));
            logger.log(Level.INFO, "Generate UPDATE query");
            System.out.println(queryBuilder.generateDeleteQuery("id = 1"));
            logger.log(Level.INFO, "Generate DELETE query");
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }


        logger.log(Level.INFO, "SQL Query Builder example finished");
    }
}