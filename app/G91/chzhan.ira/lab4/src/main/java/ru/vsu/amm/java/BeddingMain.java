package ru.vsu.amm.java;

import java.util.logging.Logger;

import ru.vsu.amm.java.entity.BeddingRecord;
import ru.vsu.amm.java.enums.*;
import ru.vsu.amm.java.service.BeddingAnalysis;
import ru.vsu.amm.java.service.BeddingData;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class BeddingMain {
    private static final Logger logger = Logger.getLogger(BeddingMain.class.getName());
    public static void main(String[] args) {
        String filePath = "C:\\Users\\yasya\\IdeaProjects\\amm-java-9-91-2024\\app\\G91\\chzhan.ira\\lab4\\bedding.txt";
        BeddingData dataLoader = new BeddingData();
        List<BeddingRecord> records = dataLoader.loadData(filePath);
        if (records == null || records.isEmpty()) {
            logger.log(Level.SEVERE, "No data loaded");
            return;
        }

        BeddingAnalysis analysis = new BeddingAnalysis();
        logger.log(Level.INFO, "Material Sizes: {0}", analysis.findMaterialSizes(records));
        Set<Colors> allColors = records.stream().map(BeddingRecord::getColor).collect(Collectors.toSet());
        logger.log(Level.INFO, "Sets with all colors ({0}) : {1}", new Object[]{allColors, analysis.findfullColorSets(records, allColors)});
        logger.log(Level.INFO, "Sales by quarter: {0}", analysis.countSalesQuarter(records));
    }
}