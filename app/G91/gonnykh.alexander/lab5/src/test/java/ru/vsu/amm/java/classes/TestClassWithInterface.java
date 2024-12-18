package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.ExtractInterface;

@ExtractInterface
public class TestClassWithInterface  implements Runnable{
    @Override
    public void run() {
        System.out.println("Running");
    }
}
