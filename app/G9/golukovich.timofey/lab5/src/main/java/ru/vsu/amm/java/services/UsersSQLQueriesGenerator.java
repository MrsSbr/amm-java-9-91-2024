package ru.vsu.amm.java.services;

import ru.vsu.amm.java.annotations.Table;
import ru.vsu.amm.java.entities.SelectUserEntity;
import ru.vsu.amm.java.entities.UpdateUserEntity;
import ru.vsu.amm.java.entities.UserEntity;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UsersSQLQueriesGenerator {
    public static String createUser(UserEntity user) {
        var annotation = UserEntity.class.getDeclaredAnnotation(Table.class);
        var tableName = annotation.name();
        var fields = UserEntity.class.getDeclaredFields();

        var fieldsNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        var fieldsValues = Arrays.stream(fields)
                .map(it -> {
                    it.setAccessible(true);
                    try {
                        Object val = it.get(user);
                        return formatType(val);
                    } catch (IllegalAccessException e) {
                        System.out.printf("Something went wrong: %s%n\n", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, fieldsNames, fieldsValues);
    }

    public static String getUsers(SelectUserEntity select) {
        var selectedFields = Arrays.stream(SelectUserEntity.class.getDeclaredFields())
                .filter(it -> {
                    it.setAccessible(true);
                    try {
                        Object val = it.get(select);
                        return (Boolean) val;
                    } catch (IllegalAccessException e) {
                        System.out.printf("Something went wrong: %s%n\n", e.getMessage());
                        throw new RuntimeException(e);
                    }
                })
                .map(Field::getName).toList();

        var annotation = UserEntity.class.getDeclaredAnnotation(Table.class);
        var tableName = annotation.name();
        var fields = UserEntity.class.getDeclaredFields();

        var fieldsNames = selectedFields.isEmpty()
                ? "*"
                : Arrays.stream(fields)
                .map(Field::getName)
                .filter(selectedFields::contains)
                .collect(Collectors.joining(", "));

        return String.format("SELECT %s FROM %s;", fieldsNames, tableName);
    }

    public static String updateUser(int id, UpdateUserEntity update) {
        var updatedFields = Arrays.stream(UpdateUserEntity.class.getDeclaredFields())
                .filter(it -> {
                    it.setAccessible(true);
                    try {
                        Object val = it.get(update);
                        return val != null;
                    } catch (IllegalAccessException e) {
                        System.out.printf("Something went wrong: %s%n\n", e.getMessage());
                        throw new RuntimeException(e);
                    }
                })
                .map(it -> {
                    it.setAccessible(true);
                    try {
                        Object val = it.get(update);
                        return String.format("%s = %s", it.getName(), formatType(val));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }})
                .collect(Collectors.joining(", "));

        var annotation = UserEntity.class.getDeclaredAnnotation(Table.class);
        var tableName = annotation.name();

        return String.format("UPDATE %s SET %s WHERE id = %s;", tableName, updatedFields, id);
    }

    public static String deleteUsers() {
        var annotation = UserEntity.class.getDeclaredAnnotation(Table.class);
        var tableName = annotation.name();

        return String.format("DELETE FROM %s;", tableName);
    }

    private static String formatType(Object value) {
        if (value instanceof Integer) {
            return value.toString();
        }
        if (value instanceof String) {
            return String.format("'%s'", value);
        }
        if (value instanceof LocalDate) {
            return String.format("'%s'", value);
        }
        return null;
    }
}
