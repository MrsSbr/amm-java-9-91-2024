package ru.vsu.amm.java;

import java.util.logging.Logger;

import ru.vsu.amm.java.entity.BeddingRecord;
import ru.vsu.amm.java.enums.Material;
import ru.vsu.amm.java.enums.Size;
import ru.vsu.amm.java.enums.Colors;
import ru.vsu.amm.java.service.BeddingAnalysis;
import ru.vsu.amm.java.service.BeddingData;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class BeddingMain {
    private static final String filePath = "app\\G91\\chzhan.ira\\lab4\\bedding.txt";
    private static final Logger logger = Logger.getLogger(BeddingMain.class.getName());

    public static void main(String[] args) {
        BeddingData dataLoader = new BeddingData();
        List<BeddingRecord> records = dataLoader.loadData(filePath);
        if (records == null || records.isEmpty()) {
            logger.log(Level.SEVERE, "No data loaded");
            return;
        }

        BeddingAnalysis analysis = new BeddingAnalysis();
        System.out.println(analysis.findMaterialSizes(records));
        Set<Colors> allColors = records.stream().map(BeddingRecord::getColor).collect(Collectors.toSet());
        System.out.println(analysis.findfullColorSets(records, allColors));
        System.out.println(analysis.countSalesQuarter(records));
    }
}