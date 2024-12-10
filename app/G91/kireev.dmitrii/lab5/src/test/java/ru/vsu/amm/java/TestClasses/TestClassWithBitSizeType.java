package ru.vsu.amm.java.TestClasses;

import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.SizeType;

@SizeUnit(sizeType = SizeType.BIT)
public  class TestClassWithBitSizeType {
    int a;
    boolean b;
    char c;
}
