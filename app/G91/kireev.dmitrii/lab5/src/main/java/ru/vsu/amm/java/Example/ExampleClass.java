package ru.vsu.amm.java.Example;

import ru.vsu.amm.java.Annotation.SizeUnit;
import ru.vsu.amm.java.SizeType.SizeType;

import java.util.ArrayList;
import java.util.Map;

@SizeUnit(sizeType = SizeType.BYTE)
public class ExampleClass {

    private String a;
    private int x;
    private ArrayList<Integer> list;
    private Map<String, Integer> map;
}
