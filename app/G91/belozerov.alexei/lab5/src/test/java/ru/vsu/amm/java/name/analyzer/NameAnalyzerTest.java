package ru.vsu.amm.java.name.analyzer;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.scanner.ClassScanner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameAnalyzerTest {

    @Test
    void nullableListTest() {
        NameAnalyzer analyzer = new NameAnalyzer();
        assertEquals("Not found classes", analyzer.analyze(null));
    }

    @Test
    void emptyListTest() {
        NameAnalyzer analyzer = new NameAnalyzer();
        assertEquals("Not found classes", analyzer.analyze(new ArrayList<>()));
    }

    @Test
    void rightNameTest() {
        ClassScanner scanner = new ClassScanner("src/test/java");
        NameAnalyzer analyzer = new NameAnalyzer();
        List<Class<?>> classes = scanner.scan("ru.vsu.amm.java.name.analyzer.right.name");
        assertEquals("", analyzer.analyze(classes));
    }

    @Test
    void badNameTest() {
        ClassScanner scanner = new ClassScanner("src/test/java");
        NameAnalyzer analyzer = new NameAnalyzer();
        List<Class<?>> classes = scanner.scan("ru.vsu.amm.java.name.analyzer.bad.name");
        String expected = """
                Class ru.vsu.amm.java.name.analyzer.bad.name.BadNamedClass123 is not named by the Java convention
                Const field pi is not named by the Java convention
                Field Name is not named by the Java convention
                Method GetName is not named by the Java convention
                """;
        assertEquals(expected, analyzer.analyze(classes));
    }
}