package ru.vsu.amm.java.TestClasses;

import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.SizeType;

@SizeUnit(sizeType = SizeType.BYTE)
public class TestClassWithByteSizeType {
    int a;
    boolean b;
    char c;
}
