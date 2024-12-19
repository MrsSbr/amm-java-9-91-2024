package ru.vsu.amm.java;

import java.lang.reflect.Field;

public class JsonSerializer {
    public static String serialize(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder json = new StringBuilder();
        json.append("{\n");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            String name = field.getName();
            Object value = field.get(obj);

            json.append("  \"").append(name).append("\": ");
            json.append(formatValue(value));

            if (i < fields.length - 1) {
                json.append(",\n");
            }
        }

        json.append("\n}");
        return json.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String || value instanceof Character) {
            return "\"" + value + "\"";
        } else if (value instanceof Boolean || value instanceof Number || value.getClass().isEnum()) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + value.getClass());
        }
    }
}
