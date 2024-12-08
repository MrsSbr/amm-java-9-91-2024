package ru.vsu.amm.java;

import ru.vsu.amm.java.collector.StatisticCollector;
import ru.vsu.amm.java.name.analyzer.NameAnalyzer;
import ru.vsu.amm.java.scanner.ClassScanner;

import java.util.List;

public class Main {

    private static final String PACKAGE = "ru.vsu.amm.java";
    private static final String PATH = "app/G91/belozerov.alexei/lab5/src/main/java";

    public static void main(String[] args) {
        ClassScanner classScanner = new ClassScanner(PATH);
        List<Class<?>> classes = classScanner.scan(PACKAGE);
        NameAnalyzer nameAnalyzer = new NameAnalyzer();
        StatisticCollector statisticCollector = nameAnalyzer.analyze(classes);
        List<String> classesList = statisticCollector.classStatistic();
        List<String> packagesList = statisticCollector.packageStatistic();
        List<String> methodsList = statisticCollector.methodStatistic();
        List<String> fieldsList = statisticCollector.fieldStatistic();
        if (classesList.isEmpty()) {
            System.out.println("All classes are named correctly.");
        } else {
            classesList.forEach(System.out::println);
        }
        if (packagesList.isEmpty()) {
            System.out.println("All packages are named correctly.");
        } else {
            packagesList.forEach(System.out::println);
        }
        if (methodsList.isEmpty()) {
            System.out.println("All methods are named correctly.");
        } else {
            methodsList.forEach(System.out::println);
        }
        if (fieldsList.isEmpty()) {
            System.out.println("All fields are named correctly.");
        } else {
            fieldsList.forEach(System.out::println);
        }
    }
}