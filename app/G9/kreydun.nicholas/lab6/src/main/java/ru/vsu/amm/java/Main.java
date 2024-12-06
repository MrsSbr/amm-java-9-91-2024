package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.ModuleAssembler;
import ru.vsu.amm.java.classes.PartProducer;
import ru.vsu.amm.java.classes.ScrewAssembler;
import ru.vsu.amm.java.enums.Part;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int milliseconds = 15000;

        BlockingQueue<Part> partsQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Part> modulesQueue = new LinkedBlockingQueue<>();

        PartProducer producer1 = new PartProducer(partsQueue, Part.PART1);
        PartProducer producer2 = new PartProducer(partsQueue, Part.PART2);
        PartProducer producer3 = new PartProducer(partsQueue, Part.PART3);
        ModuleAssembler assembler = new ModuleAssembler(partsQueue, modulesQueue);
        ScrewAssembler screwAssembler = new ScrewAssembler(partsQueue, modulesQueue);

        producer1.start();
        producer2.start();
        producer3.start();
        assembler.start();
        screwAssembler.start();

        Thread.sleep(milliseconds);

        producer1.interrupt();
        producer2.interrupt();
        producer3.interrupt();
        assembler.interrupt();
        screwAssembler.interrupt();
    }
}