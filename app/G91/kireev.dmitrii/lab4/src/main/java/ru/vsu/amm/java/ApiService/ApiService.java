package ru.vsu.amm.java.ApiService;

import ru.vsu.amm.java.Model.FileEntity;
import ru.vsu.amm.java.Model.TortureInstrument;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class ApiService {


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
        if (entities == null || entities.isEmpty())  return new ArrayList<>();


        Map<String, List<FileEntity>> groupedBySuspect = entities.stream()
                .collect(Collectors.groupingBy(FileEntity::suspectName));

        return groupedBySuspect.entrySet().stream()
                .filter(entry -> {
                    List<FileEntity> suspectEntities = entry.getValue();
                    Set<TortureInstrument> instrumentsUsed = suspectEntities.stream()
                            .filter(entity -> !entity.confessionObtained())
                            .map(FileEntity::tortureInstrument)
                            .collect(Collectors.toSet());
                    boolean allInstrumentsUsed = instrumentsUsed.size() == TortureInstrument.values().length;
                    boolean noConfession = suspectEntities.stream()
                            .noneMatch(FileEntity::confessionObtained);
                    return allInstrumentsUsed && noConfession;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
