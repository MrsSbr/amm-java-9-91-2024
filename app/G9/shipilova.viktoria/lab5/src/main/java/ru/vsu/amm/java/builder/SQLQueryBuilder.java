package ru.vsu.amm.java.builder;

import ru.vsu.amm.java.table.Column;
import ru.vsu.amm.java.table.Table;

import java.lang.reflect.Field;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SQLQueryBuilder {
    private static Logger logger = Logger.getLogger(SQLQueryBuilder.class.getName());

    private Class<?> entityClass;

    public SQLQueryBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String generateInsertQuery(Object entity) throws IllegalAccessException {
        Table table = entityClass.getAnnotation(Table.class);
        if(table == null){
            logger.log(Level.SEVERE, "Table not found in class " + entityClass.getName());
            return null;
        }
        StringBuilder query = new StringBuilder("INSERT INTO " + table.name() + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        Field[] fields = entityClass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Column column = fields[i].getAnnotation(Column.class);
            if(column != null){
                query.append(column.name());
                fields[i].setAccessible(true);
                values.append("'").append(fields[i].get(entity)).append("'");
                if(i < fields.length - 1){
                    query.append(", ");
                    values.append(", ");
                }
            }
        }

        query.append(")").append(values).append(")");

        logger.log(Level.INFO,"Generated INSERT query: {0}" ,query.toString());
        return query.toString();
    }

    public String generateSelectQuery(String condition){
        Table table = entityClass.getAnnotation(Table.class);
        if(table == null){
            logger.log(Level.SEVERE, "Table not found in class " + entityClass.getName());
        }

        StringBuilder query = new StringBuilder("SELECT * FROM " + table.name() + " WHERE " + condition);
        logger.log(Level.INFO,"Generated SELECT query: {0}" ,query.toString());
        return query.toString();
    }

    public String generateUpdateQuery(Object entity, String condition) throws IllegalAccessException {
        Table table = entityClass.getAnnotation(Table.class);
        if(table == null){
            logger.log(Level.SEVERE, "Table not found in class " + entityClass.getName());
            return null;
        }

        StringBuilder query = new StringBuilder("UPDATE " + table.name() + " SET ");

        Field[] fields = entityClass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Column column = fields[i].getAnnotation(Column.class);
            if(column != null){
                fields[i].setAccessible(true);
                query.append(column.name()).append(" = '").append(fields[i].get(entity)).append("'");
                if(i < fields.length - 1){
                    query.append(", ");
                }
            }
        }

        query.append(" WHERE ").append(condition);

        logger.log(Level.INFO,"Generated UPDATE query: {0}" ,query.toString());
        return query.toString();
    }

    public String generateDeleteQuery(String condition){
        Table table = entityClass.getAnnotation(Table.class);
        if(table == null){
            logger.log(Level.SEVERE, "Table not found in class " + entityClass.getName());
        }

        StringBuilder query = new StringBuilder("DELETE FROM " + table.name() + " WHERE " + condition);

        logger.log(Level.INFO,"Generated DELETE query: {0}" ,query.toString());
        return query.toString();
    }
}
