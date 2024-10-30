package ru.vsu.amm.java.Factories;

import ru.vsu.amm.java.Enums.HttpResponseCode;
import ru.vsu.amm.java.Entities.Log;
import ru.vsu.amm.java.Enums.Resource;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogCreator {
    public LogCreator() {
    }

    public Log create() {
        Random random = new Random();

        int year = random.nextInt(1991, LocalDate.now().getYear()) + 1;
        int month = random.nextInt(0, 12) + 1;
        int day = random.nextInt(0, Month.of(month).length(Year.isLeap(year))) + 1;
        LocalDate date = LocalDate.of(year, month, day);

        Resource resource = Resource.values()[random.nextInt(0,
                Resource.values().length)];

        int ip = random.nextInt(1, 1000000);

        HttpResponseCode responseCode = HttpResponseCode.values()[random.nextInt(0,
                HttpResponseCode.values().length)];

        return new Log(date, resource, ip, responseCode);
    }

    public Log create(LocalDate date, Resource resource, int ip, HttpResponseCode responseCode) {
        return new Log(date, resource, ip, responseCode);
    }

    public List<Log> create(int count) {
        List<Log> logs = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            logs.add(create());
        }
        return logs;
    }
}
