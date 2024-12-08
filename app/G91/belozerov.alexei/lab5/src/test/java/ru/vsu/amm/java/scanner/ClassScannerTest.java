package ru.vsu.amm.java.scanner;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassScannerTest {

    @Test
    void notEligiblePackageNameTest() {
        ClassScanner scanner = new ClassScanner("123");
        assertEquals(0, scanner.scan("123").size());
    }

    @Test
    void eligiblePackageNameTest() {
        ClassScanner scanner = new ClassScanner("src/test/java");
        List<Class<?>> result = scanner.scan("ru.vsu.amm.java.scanner");
        assertEquals(1, result.size());
        assertEquals("ClassScannerTest", result.getFirst().getSimpleName());
    }
}