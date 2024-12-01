package ru.vsu.amm.java.poetry;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Poem {
    private static final Logger log = Logger.getLogger(Poem.class.getName());
    private ConcurrentLinkedQueue<String> lines;

    public Poem() {
        this.lines = new ConcurrentLinkedQueue<>();
    }

    public void addLine(String line) {
        lines.add(line);
        log.info("������ ��������� � �������������: " + line);
    }

    public void printPoem() {
        log.info("������ �������������...");
        System.out.println("���������� �������������:");
        lines.forEach(System.out::println);
    }

    public void savePoem(String fileName) {
        try {
            Files.write(Paths.get(fileName), lines);
            log.info("������������� ��������� � ����: " + fileName);
        } catch (IOException e) {
            log.log(Level.SEVERE, "������ ��� ���������� ������������� � ����: " + fileName, e);
        }
    }
}
