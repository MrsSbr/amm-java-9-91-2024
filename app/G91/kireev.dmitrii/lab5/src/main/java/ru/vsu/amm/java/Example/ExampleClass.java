package ru.vsu.amm.java.Example;

import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.BitSizeType;
import ru.vsu.amm.java.SizeType.KilobyteSizeType;

import java.util.ArrayList;
import java.util.Map;

@SizeUnit(sizeType = KilobyteSizeType.class)
public class ExampleClass {

    private String a;
    private int x;
    private ArrayList<Integer> list;
    private Map<String, Integer> map;
}
