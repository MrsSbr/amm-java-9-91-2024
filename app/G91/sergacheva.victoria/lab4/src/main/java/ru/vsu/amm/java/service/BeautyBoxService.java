package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.BeautyBox;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BeautyBoxService {

    private static final Logger log;

    static {
        log = Logger.getLogger(BeautyBoxService.class.getName());
    }

    public Map<String, Long> calculateProductFrequency(List<BeautyBox> beautyBoxes) {
        log.info("Calculate product frequency");
        return beautyBoxes.stream()
                .flatMap(box -> box.getContents().stream())
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));
    }

    public String findBestSalesMonth(List<BeautyBox> beautyBoxes) {
        log.info("Find best sales month");
        return beautyBoxes.stream()
                .collect(Collectors.groupingBy(
                        box -> box.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                        Collectors.summingInt(BeautyBox::getCountSold)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }

    public Map<String, String> findFirstAppearanceByMonth(List<BeautyBox> beautyBoxes) {
        log.info("Find first appearance by month");
        return beautyBoxes.stream()
                .flatMap(box -> box.getContents().stream()
                        .map(product -> Map.entry(product, box.getDate())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (date1, date2) -> date1.isBefore(date2) ? date1 : date2
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                ));
    }
}
