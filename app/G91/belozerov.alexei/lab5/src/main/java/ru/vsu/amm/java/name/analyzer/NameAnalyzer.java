package ru.vsu.amm.java.name.analyzer;

import ru.vsu.amm.java.collector.StatisticCollector;
import ru.vsu.amm.java.collector.StatisticCollectorBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameAnalyzer {

    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("^[A-Z_]+$");
    private static final Pattern CAMELCASE_PATTERN = Pattern.compile("^[a-z]+([A-Z]([a-z]+)?)*$");
    private static final Pattern PASCALCASE_PATTERN = Pattern.compile("^([A-Z]([a-z]+)?)+$");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("^[a-z.]+$");

    public StatisticCollector analyze(List<Class<?>> classes) {
        if (classes == null || classes.isEmpty()) {
            return new StatisticCollectorBuilder().build();
        }
        StatisticCollectorBuilder builder = new StatisticCollectorBuilder();
        for (Class<?> clazz : classes) {
            builder.addPackageStatistic(analyzePackage(clazz))
                .addClassStatistic(analyzeClass(clazz))
                .addFieldStatistic(analyzeFields(clazz))
                .addMethodStatistic(analyzeMethods(clazz));
        }
        return builder.build();
    }

    private List<String> analyzeClass(Class<?> clazz) {
        List<String> classes = new ArrayList<>();
        Matcher matcher = PASCALCASE_PATTERN.matcher(clazz.getSimpleName());
        if (!matcher.matches()) {
            classes.add("Class " + clazz.getName() + " is not named by the Java convention\n");
        }
        return classes;
    }

    private List<String> analyzeFields(Class<?> clazz) {
        List<String> fieldsList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                Matcher constMatcher = UPPERCASE_PATTERN.matcher(field.getName());
                if (!constMatcher.matches()) {
                    fieldsList.add("Const field " + field.getName() + " is not named by the Java convention\n");
                }
            } else {
                Matcher matcher = CAMELCASE_PATTERN.matcher(field.getName());
                if (!matcher.matches()) {
                    fieldsList.add("Field " + field.getName() + " is not named by the Java convention\n");
                }
            }
        }
        return fieldsList;
    }

    private List<String> analyzeMethods(Class<?> clazz) {
        List<String> methodsList = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Matcher matcher = CAMELCASE_PATTERN.matcher(method.getName());
            if (!matcher.matches()) {
                methodsList.add("Method " + method.getName() + " is not named by the Java convention\n");
            }
        }
        return methodsList;
    }

    private List<String> analyzePackage(Class<?> clazz) {
        List<String> packages = new ArrayList<>();
        Matcher matcher = LOWERCASE_PATTERN.matcher(clazz.getPackageName());
        if (!matcher.matches()) {
            packages.add("Package " + clazz.getPackageName() + " is not named by the Java convention\n");
        }
        return packages;
    }
}

