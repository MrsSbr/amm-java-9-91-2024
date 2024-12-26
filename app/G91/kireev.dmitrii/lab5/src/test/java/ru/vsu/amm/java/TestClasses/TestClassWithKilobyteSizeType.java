package ru.vsu.amm.java.TestClasses;

import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.SizeType;

@SizeUnit(sizeType = SizeType.KILOBYTE)
public class TestClassWithKilobyteSizeType {
    int a;
    boolean b;
    char c;
}
