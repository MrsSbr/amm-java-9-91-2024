package ru.vsu.amm.java.name.analyzer;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.collector.StatisticCollector;
import ru.vsu.amm.java.scanner.ClassScanner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameAnalyzerTest {

    @Test
    void nullableListTest() {
        NameAnalyzer analyzer = new NameAnalyzer();
        StatisticCollector collector = analyzer.analyze(null);
        assertEquals(0, collector.classStatistic().size());
        assertEquals(0, collector.fieldStatistic().size());
        assertEquals(0, collector.methodStatistic().size());
        assertEquals(0, collector.packageStatistic().size());
    }

    @Test
    void emptyListTest() {
        NameAnalyzer analyzer = new NameAnalyzer();
        StatisticCollector collector = analyzer.analyze(new ArrayList<>());
        assertEquals(0, collector.classStatistic().size());
        assertEquals(0, collector.fieldStatistic().size());
        assertEquals(0, collector.methodStatistic().size());
        assertEquals(0, collector.packageStatistic().size());
    }

    @Test
    void rightNameTest() {
        ClassScanner scanner = new ClassScanner("src/test/java");
        NameAnalyzer analyzer = new NameAnalyzer();
        List<Class<?>> classes = scanner.scan("ru.vsu.amm.java.name.analyzer.right.name");
        StatisticCollector collector = analyzer.analyze(classes);
        assertEquals(0, collector.classStatistic().size());
        assertEquals(0, collector.fieldStatistic().size());
        assertEquals(0, collector.methodStatistic().size());
        assertEquals(0, collector.packageStatistic().size());
    }

    @Test
    void badNameTest() {
        ClassScanner scanner = new ClassScanner("src/test/java");
        NameAnalyzer analyzer = new NameAnalyzer();
        List<Class<?>> classes = scanner.scan("ru.vsu.amm.java.name.analyzer.bad.name");
        StatisticCollector collector = analyzer.analyze(classes);
        assertEquals(1, collector.classStatistic().size());
        assertEquals(2, collector.fieldStatistic().size());
        assertEquals(1, collector.methodStatistic().size());
        assertEquals(0, collector.packageStatistic().size());
    }
}