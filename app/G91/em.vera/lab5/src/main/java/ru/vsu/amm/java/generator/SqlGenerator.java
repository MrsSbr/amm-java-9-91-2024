package ru.vsu.amm.java.generator;

import ru.vsu.amm.java.annotation.Table;
import ru.vsu.amm.java.entity.TeaBag;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SqlGenerator {

    private static final Logger logger = Logger.getLogger(SqlGenerator.class.getName());

    public String update(TeaBag teaBag) {
        String tableName = TeaBag.class.getAnnotation(Table.class).name();
        Field[] fields = TeaBag.class.getDeclaredFields();
        String updatingColumns = Arrays.stream(fields)
                .filter(field -> {
                    field.setAccessible(true);
                    try {
                        return !field.getName().equals("id") && field.get(teaBag) != null;
                    } catch (IllegalAccessException e) {
                        logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
                        System.out.println("Could not get access to field: " + field.getName() + "\n");
                    }
                    return false;
                })
                .map(field -> {
                    try {
                        return field.getName() + " = " + field.get(teaBag);
                    } catch (IllegalAccessException e) {
                        logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
                        System.out.println("Could not get access to field: " + field.getName() + "\n");
                    }
                    return "";
                })
                .collect(Collectors.joining(", "));
        if (updatingColumns.isEmpty()) {
            return "";
        } else if (teaBag.id() == null) {
            return "Update " + tableName + " SET " + updatingColumns + ";";
        } else {
            return "Update " + tableName + " SET " + updatingColumns + " WHERE id = " + teaBag.id() + ";";
        }
    }

    public String select(String column) {
        String tableName = TeaBag.class.getAnnotation(Table.class).name();
        try {
            Field field = TeaBag.class.getDeclaredField(column);
            return "SELECT " + field.getName() + " FROM " + tableName + ";";
        } catch (NoSuchFieldException e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            System.out.println("Could not find field with such name\n");

        }
        return "";
    }
}
