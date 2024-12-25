package ru.vsu.amm.java.Analyzer;

import ru.vsu.amm.java.Annotation.SizeUnit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassSizeAnalyzer {

    private final static int DEFAULT_SIZE = 0;

    public static double analyzeClassSize(Class<?> clazz) {

        double size = 0;
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                size += getFieldSize(field.getType());
            }
        }

        if (clazz.isAnnotationPresent(SizeUnit.class)) {

            SizeUnit sizeUnit = clazz.getAnnotation(SizeUnit.class);
            size /= sizeUnit.sizeType().getSizeInBits();

        }

        return size;
    }

    private static double getFieldSize(Class<?> type) {

        if (type.isPrimitive()) {
            return getPrimitiveFieldSize(type);
        } else {
            return analyzeClassSize(type);
        }
    }

    public static int getPrimitiveFieldSize(Class<?> type) {
        return switch (type.getName()) {
            case "boolean" -> Byte.SIZE;
            case "byte" -> Byte.SIZE;
            case "short" -> Short.SIZE;
            case "char" -> Character.SIZE;
            case "int" -> Integer.SIZE;
            case "float" -> Float.SIZE;
            case "long" -> Long.SIZE;
            case "double" -> Double.SIZE;
            default -> DEFAULT_SIZE;
        };
    }


}

