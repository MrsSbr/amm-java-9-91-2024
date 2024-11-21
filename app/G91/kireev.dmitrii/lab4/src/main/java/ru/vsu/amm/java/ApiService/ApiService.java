package ru.vsu.amm.java.ApiService;

import ru.vsu.amm.java.Model.FileEntity;
import ru.vsu.amm.java.Model.TortureInstrument;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return (entities == null || entities.isEmpty()) ? new ArrayList<>() :

                entities.stream()
                        .filter(entity -> !entity.confessionObtained())
                        .collect(Collectors.groupingBy(
                                FileEntity::suspectName,
                                Collectors.mapping(FileEntity::tortureInstrument, Collectors.toSet())
                        )).entrySet().stream().filter(entry -> entry.getValue().size() == 4).map(Map.Entry::getKey).toList();


    }

}
