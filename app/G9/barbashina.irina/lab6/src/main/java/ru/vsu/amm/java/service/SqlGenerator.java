package ru.vsu.amm.java.service;

import ru.vsu.amm.java.annotation.Column;
import ru.vsu.amm.java.annotation.Id;
import ru.vsu.amm.java.annotation.JoinColumn;
import ru.vsu.amm.java.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlGenerator {
    public <T> String generateSelectQuery(Class<T> entityClass) {
        return generateSelectQuery(entityClass, null, null);
    }

    public <T,U> String generateSelectQuery(Class<T> entityClass, Class<U> joinEntityClass, String joinEntityField) {
        if(!entityClass.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Entity class must be annotated with @Table");
        }

        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        String tableName = tableAnnotation.name();

        List<String> columns = getColumns(entityClass);

        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(columns.stream().map(c -> tableName + "." + c).collect(Collectors.joining(", ")));

        if(joinEntityClass != null && joinEntityField != null){
            String joinCondition = createJoin(entityClass, joinEntityClass, joinEntityField);
            sb.append(createJoinSelect(joinEntityClass,tableName,joinCondition));
        }
        sb.append(" FROM ").append(tableName);

        if(joinEntityClass != null && joinEntityField != null){
            String joinCondition = createJoin(entityClass, joinEntityClass, joinEntityField);
            sb.append(" ").append(joinCondition);
        }
        return sb.toString();
    }

    private String createJoinSelect(Class<?> joinEntityClass, String tableName, String joinCondition) {
        if(!joinEntityClass.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Join entity class must be annotated with @Table");
        }

        Table tableAnnotation = joinEntityClass.getAnnotation(Table.class);
        String joinTableName = tableAnnotation.name();
        List<String> columns = getColumns(joinEntityClass);
        return "," + columns.stream().map(c -> joinTableName + "." + c).collect(Collectors.joining(", "));
    }

    private <T,U> String createJoin(Class<T> entityClass, Class<U> joinEntityClass, String joinEntityField) {
        try {
            Field field = entityClass.getDeclaredField(joinEntityField);
            if(!field.isAnnotationPresent(JoinColumn.class)) {
                throw new IllegalArgumentException("Field must be annotated with @JoinColumn");
            }

            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            String joinTableName = joinEntityClass.getAnnotation(Table.class).name();
            String tableName = entityClass.getAnnotation(Table.class).name();
            return String.format("JOIN %s ON %s.%s = %s.%s",
                        joinTableName,
                        tableName,
                        joinColumn.name(),
                        joinTableName,
                    joinColumn.targetColumn());
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field '" + joinEntityField + "' not found", e);
        }
    }

    private <T> List<String> getColumns(Class<T> entityClass) {
        List<String> columns = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                columns.add(columnAnnotation.name());
            } else if (field.isAnnotationPresent(Id.class)) {
                columns.add(field.getName());
            }
        }
        return columns;
    }
}

