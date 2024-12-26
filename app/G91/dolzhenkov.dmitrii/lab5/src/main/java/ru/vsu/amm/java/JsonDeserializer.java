package ru.vsu.amm.java;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonDeserializer {

    public static <T> T deserialize(String json, Class<T> clazz) throws Exception {
        if (json == null) {
            throw new IllegalArgumentException("Invalid JSON string");
        }

        Map<String, String> jsonMap = parseJsonToMap(json);
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            if (jsonMap.containsKey(fieldName)) {
                String fieldValue = jsonMap.get(fieldName);
                Object value = parseValue(fieldValue, field.getType());
                field.set(instance, value);
            }
        }

        return instance;
    }

    private static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim().substring(1, json.length() - 1);

        String[] entries = json.split(",\n");
        for (String entry : entries) {
            String[] pair = entry.split(": ", 2);
            String key = pair[0].trim().replace("\"", "");
            String value = pair[1].trim();
            map.put(key, value);
        }
        return map;
    }

    private static Object parseValue(String value, Class<?> type) {
        if (value.equals("null")) {
            return null;
        } else if (type == String.class) {
            return value.replace("\"", "");
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type.isEnum()) {
            return Enum.valueOf((Class<? extends Enum>) type, value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + type);
        }
    }
}
