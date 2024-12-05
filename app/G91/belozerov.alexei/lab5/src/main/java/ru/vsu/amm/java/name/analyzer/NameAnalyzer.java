package ru.vsu.amm.java.name.analyzer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameAnalyzer {

    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("^[A-Z_]+$");
    private static final Pattern CAMELCASE_PATTERN = Pattern.compile("^[a-z]+([A-Z]([a-z]+)?)*$");
    private static final Pattern PASCALCASE_PATTERN = Pattern.compile("^([A-Z]([a-z]+)?)+$");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("^[a-z.]+$");

    public String analyze(List<Class<?>> classes) {
        if (classes == null || classes.isEmpty()) {
            return "Not found classes";
        }
        StringBuilder sb = new StringBuilder();
        for (Class<?> clazz : classes) {
            sb.append(analyzePackage(clazz));
            sb.append(analyzeClass(clazz));
            sb.append(analyzeFields(clazz));
            sb.append(analyzeMethods(clazz));
        }
        if (sb.isEmpty()) {
            sb.append("Names are called by the Java convention");
        }
        return sb.toString();
    }

    private StringBuilder analyzeClass(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = PASCALCASE_PATTERN.matcher(clazz.getSimpleName());
        if (!matcher.matches()) {
            sb.append("Class ").append(clazz.getName()).append(" is not named by the Java convention\n");
        }
        return sb;
    }

    private StringBuilder analyzeFields(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                Matcher constMatcher = UPPERCASE_PATTERN.matcher(field.getName());
                if (!constMatcher.matches()) {
                    sb.append("Const field ").append(field.getName()).append(" is not named by the Java convention\n");
                }
            } else {
                Matcher matcher = CAMELCASE_PATTERN.matcher(field.getName());
                if (!matcher.matches()) {
                    sb.append("Field ").append(field.getName()).append(" is not named by the Java convention\n");
                }
            }
        }
        return sb;
    }

    private StringBuilder analyzeMethods(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Matcher matcher = CAMELCASE_PATTERN.matcher(method.getName());
            if (!matcher.matches()) {
                sb.append("Method ").append(method.getName()).append(" is not named by the Java convention\n");
            }
        }
        return sb;
    }

    private StringBuilder analyzePackage(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = LOWERCASE_PATTERN.matcher(clazz.getPackageName());
        if (!matcher.matches()) {
            sb.append("Package ").append(clazz.getPackageName()).append(" is not named by the Java convention\n");
        }
        return sb;
    }
}

