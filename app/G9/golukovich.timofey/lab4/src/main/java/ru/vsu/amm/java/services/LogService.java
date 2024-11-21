package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Log;
import ru.vsu.amm.java.enums.HttpResponseCode;
import ru.vsu.amm.java.enums.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class LogService {
    public static Map<HttpResponseCode, Long> getHttpResponseCodesStatistics(List<Log> logs) {
        return logs.stream()
                .collect(Collectors
                        .groupingBy(Log::httpResponseCode,
                                Collectors.counting()));
    }

    public static Map<Resource, Long> getResourceStatistics(List<Log> logs) {
        return logs.stream()
                .collect(Collectors
                        .groupingBy(Log::resource,
                                Collectors.counting()));
    }

    public static Resource getMostUnstableResource(List<Log> logs) {
        return Collections.max(
                         logs.stream()
                            .filter(x -> x.httpResponseCode() == HttpResponseCode.ServerError)
                            .collect(Collectors.groupingBy(Log::resource, Collectors.counting()))
                            .entrySet(),
                        Map.Entry.comparingByValue())
                .getKey();
    }

    public static Resource getMostStableResource(List<Log> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(
                        Log::resource,
                        Collectors.averagingLong(log -> logs.stream()
                                .filter(l -> l.resource().equals(log.resource()) &&
                                        l.httpResponseCode().equals(HttpResponseCode.Success)).count()
                                / logs.stream()
                                .filter(l -> l.resource().equals(log.resource())).count())))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NoSuchElementException("Передан пустой список")); // todo add text
    }
}
