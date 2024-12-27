package ru.vsu.amm.java.classes;

public class TestClassWithoutAnnotation implements AutoCloseable{
    @Override
    public void close() {
        System.out.println("Closing");
    }
}
