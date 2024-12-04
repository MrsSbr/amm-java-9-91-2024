package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Model.FileEntity;
import ru.vsu.amm.java.Model.TortureInstrument;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {


    public Map<TortureInstrument, Long> getConfessionCountPerInstrument(List<FileEntity> entities) {

        return (entities == null || entities.isEmpty()) ? new HashMap<>() :

                entities.stream()
                        .filter(FileEntity::confessionObtained)
                        .collect(Collectors.groupingBy(
                                FileEntity::tortureInstrument,
                                Collectors.counting()));

    }

    public Map<String, Duration> getTortureDurationPerSuspect(List<FileEntity> entities) {

        return (entities == null || entities.isEmpty()) ? new HashMap<>() :

                entities.stream()
                        .collect(Collectors.groupingBy(
                                FileEntity::suspectName,
                                Collectors.reducing(Duration.ZERO, FileEntity::tortureTime, Duration::plus)));

    }


    public List<String> getTorturedByEveryInstrumentWithoutConfession(List<FileEntity> entities) {
        if (entities == null || entities.isEmpty()) return new ArrayList<>();

        Map<String, List<FileEntity>> groupedBySuspect = entities.stream()
                .collect(Collectors.groupingBy(FileEntity::suspectName));

        return groupedBySuspect.entrySet().stream()
                .filter(suspect -> suspect.getValue().stream().noneMatch(FileEntity::confessionObtained))
                .filter(suspect -> suspect.getValue().stream().map(FileEntity::tortureInstrument)
                        .collect(Collectors.toSet())
                        .size() == TortureInstrument.values().length)
                .map(Map.Entry::getKey).toList();

    }
}
