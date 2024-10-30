package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.Log;
import ru.vsu.amm.java.Enums.HttpResponseCode;
import ru.vsu.amm.java.Enums.Resource;

import java.util.*;
import java.util.stream.Collectors;

public class LogService {
    private List<Log> logs;

    public LogService(String logFileName) {

    }

    public void changeLogFile(String logFileName) {
        logs.clear();

        
    }

    public Map<HttpResponseCode, Long> getHttpResponseCodesStatistics() {
        return logs.stream().collect(Collectors.groupingBy(Log::httpResponseCode, Collectors.counting()));
    }

    public Map<Resource, Long> getUriStatistics() {
        return logs.stream().collect(Collectors.groupingBy(Log::resource, Collectors.counting()));
    }

    public Resource getMostUnstableResource() {
        return Collections.max(logs
                .stream()
                .filter(x -> x.httpResponseCode() == HttpResponseCode.ServerError)
                .collect(Collectors.groupingBy(Log::resource, Collectors.counting()))
                .entrySet(),
                Map.Entry.comparingByValue())
                .getKey();
    }

    public Resource getMostStableResource() {
        return Resource.GitHub;
    }
}
