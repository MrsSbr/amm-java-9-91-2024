package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Analyzer.ClassSizeAnalyzer;
import ru.vsu.amm.java.TestClasses.TestClassWithBitSizeType;
import ru.vsu.amm.java.TestClasses.TestClassWithByteSizeType;
import ru.vsu.amm.java.TestClasses.TestClassWithoutAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyzerTest {


    @Test
    public void testAnalyzeClassSizeWithBitSizeType() {
        double expectedSize = (Integer.SIZE + Byte.SIZE + Character.SIZE);
        double actualSize = ClassSizeAnalyzer.analyzeClassSize(TestClassWithBitSizeType.class);
        assertEquals(expectedSize, actualSize, 0.001);
    }

    @Test
    public void testAnalyzeClassSizeWithByteSizeType() {
        double expectedSize = (Integer.SIZE + Byte.SIZE + Character.SIZE) / 8.0;
        double actualSize = ClassSizeAnalyzer.analyzeClassSize(TestClassWithByteSizeType.class);
        assertEquals(expectedSize, actualSize, 0.001);
    }

    @Test
    public void testAnalyzeClassSizeWithoutAnnotation() {
        double expectedSize = Integer.SIZE + Byte.SIZE + Character.SIZE;
        double actualSize = ClassSizeAnalyzer.analyzeClassSize(TestClassWithoutAnnotation.class);
        assertEquals(expectedSize, actualSize, 0.001);
    }
}
