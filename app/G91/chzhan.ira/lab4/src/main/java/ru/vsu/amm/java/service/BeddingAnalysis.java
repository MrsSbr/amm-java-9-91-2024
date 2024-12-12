package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.BeddingRecord;
import ru.vsu.amm.java.enums.Colors;
import ru.vsu.amm.java.enums.Material;
import ru.vsu.amm.java.enums.Size;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BeddingAnalysis {

    private static final int MONTHS_IN_QUARTER = 3;

    private static final Logger logger = Logger.getLogger(BeddingAnalysis.class.getName());

    public Map<Material, Set<Size>> findMaterialSizes(List<BeddingRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        BeddingRecord::getMaterial,
                        Collectors.mapping(BeddingRecord::getSize, Collectors.toSet())));
    }

    public Set<String> findfullColorSets(List<BeddingRecord> records, Set<Colors> allColors) {
        Map<String, Set<Colors>> nameColors = records.stream()
                .collect(Collectors.groupingBy(BeddingRecord::getName, Collectors.mapping(BeddingRecord::getColor, Collectors.toSet())));
        return nameColors.entrySet().stream()
                .filter(entry -> entry.getValue().containsAll(allColors))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


    public Map<Integer, Long> countSalesQuarter(List<BeddingRecord> records) {

        return records.stream()
                .collect(Collectors.groupingBy(record -> (record.getDate().getMonthValue() - 1) / MONTHS_IN_QUARTER + 1,
                        Collectors.counting()));
    }
}
