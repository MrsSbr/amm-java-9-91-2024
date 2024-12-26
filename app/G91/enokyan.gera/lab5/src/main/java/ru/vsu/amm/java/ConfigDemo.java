package ru.vsu.amm.java;

import ru.vsu.amm.java.service.SampleConfig;
import ru.vsu.amm.java.service.ConfigService;

public class ConfigDemo {
    public static void main(String[] args) {
        var dbConfig = ConfigService.load(SampleConfig.class);
        System.out.println(dbConfig.getString());
        System.out.println(dbConfig.getInteger());
        System.out.println(dbConfig.getDouble());
        System.out.println(dbConfig.getBoolean());
        System.out.println(dbConfig.getMyColor());
    }
}