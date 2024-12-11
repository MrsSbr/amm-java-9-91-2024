package ru.vsu.amm.java.TestClasses;

import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.SizeType;

@SizeUnit(sizeType = SizeType.BIT)
public  class TestClassWithBitSizeType {
    private int a;
    private boolean b;
    private char c;
}
