package ru.vsu.amm.java;

import ru.vsu.amm.java.util.Benchmark;
import ru.vsu.amm.java.entity.Stat;
import ru.vsu.amm.java.service.Service;
import ru.vsu.amm.java.service.ServiceImpl;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Service service = new ServiceImpl();
        Service trackedService = Benchmark.track(service);
        trackedService.do1();
        trackedService.do2();
        trackedService.do3();
        Stat stat = Benchmark.getStat(trackedService);
        stat.print(System.out);
    }
}