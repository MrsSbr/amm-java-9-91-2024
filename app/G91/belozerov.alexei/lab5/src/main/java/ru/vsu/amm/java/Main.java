package ru.vsu.amm.java;

import ru.vsu.amm.java.scanner.ClassScanner;
import ru.vsu.amm.java.name.analyzer.NameAnalyzer;

import java.util.List;

public class Main {

    private static final String PACKAGE = "ru.vsu.amm.java";
    private static final String PATH = "app/G91/belozerov.alexei/lab5/src/main/java";

    public static void main(String[] args) {
        ClassScanner classScanner = new ClassScanner(PATH);
        List<Class<?>> classes = classScanner.scan(PACKAGE);
        NameAnalyzer nameAnalyzer = new NameAnalyzer();
        String analyzingResult = nameAnalyzer.analyze(classes);
        if (analyzingResult.isEmpty()) {
            System.out.println("Names are called by the Java convention");
        }
        else {
            System.out.println(analyzingResult);
        }
    }
}