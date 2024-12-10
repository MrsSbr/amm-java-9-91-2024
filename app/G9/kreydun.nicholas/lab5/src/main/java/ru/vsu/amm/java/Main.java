package ru.vsu.amm.java;

import ru.vsu.amm.java.banchmarks.Benchmark;
import ru.vsu.amm.java.banchmarks.Stat;
import ru.vsu.amm.java.impl.ServiceImpl;
import ru.vsu.amm.java.service.Logg;
import ru.vsu.amm.java.service.Service;

public class Main {
    public static void main(String[] args) throws Exception {
        Logg.logger.info("Start program in " + Main.class.getName());
        Service service = new ServiceImpl();

        Service trackedService = Benchmark.track(service);

        trackedService.do1();
        trackedService.do2();
        trackedService.do3();

        Stat stat = Benchmark.getStat(trackedService);

        System.out.println(stat);

        Logg.logger.info("End program");
    }
}