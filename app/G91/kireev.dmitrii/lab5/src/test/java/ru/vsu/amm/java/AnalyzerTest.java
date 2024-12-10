package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Analyzer.ClassSizeAnalyzer;
import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.BitSizeType;
import ru.vsu.amm.java.SizeType.ByteSizeType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyzerTest {


    @Test
    public void testAnalyzeClassSizeWithBitSizeType() {

        @SizeUnit(sizeType = BitSizeType.class)
        class TestClass {
            int a;
            boolean b;
            char c;
        }

        double expectedSize = (Integer.SIZE + Byte.SIZE + Character.SIZE) / 1.0;
        double actualSize = ClassSizeAnalyzer.analyzeClassSize(TestClass.class);
        assertEquals(expectedSize, actualSize, 0.001);
    }

    @Test
    public void testAnalyzeClassSizeWithByteSizeType() {

        @SizeUnit(sizeType = ByteSizeType.class)
        class TestClass {
            int a;
            boolean b;
            char c;
        }

        double expectedSize = (Integer.SIZE + Byte.SIZE + Character.SIZE) / 1024.0;
        double actualSize = ClassSizeAnalyzer.analyzeClassSize(TestClass.class);
        assertEquals(expectedSize, actualSize, 0.001);
    }

    @Test
    public void testAnalyzeClassSizeWithoutAnnotation() {

        class TestClass {
            int a;
            boolean b;
            char c;
        }

        double expectedSize = Integer.SIZE + Byte.SIZE + Character.SIZE;
        double actualSize = ClassSizeAnalyzer.analyzeClassSize(TestClass.class);
        assertEquals(expectedSize, actualSize, 0.001);
    }
}
