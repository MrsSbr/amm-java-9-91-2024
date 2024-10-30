package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.BalmerPeakRecord;
import ru.vsu.amm.java.enums.AlcoholType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalmerPeakServiceTest {

    private List<BalmerPeakRecord> records;

    @BeforeEach
    void setUp() {
        records = getRecords();
    }

    @Test
    void getAverageAmountForPeak() {
        double averageAmountForPeek = BalmerPeakService.getAverageAmountForPeak(records);
        double ans = (400 + 400 + 700) / 3.;
        assertEquals(ans, averageAmountForPeek);

        double averageAmountEqualsZero = BalmerPeakService.getAverageAmountForPeak(new ArrayList<>());
        assertEquals(0, averageAmountEqualsZero);
    }

    @Test
    void getUniqueAlcoholType() {
        List<AlcoholType> types = BalmerPeakService.getUniqueAlcoholType(records);
        assertEquals(5, types.size());
        assertEquals(1, types.stream().filter((AlcoholType x) -> x == AlcoholType.Beer).count());
    }

    @Test
    void getTotalAlcoholAmount() {
        int totalAmount = BalmerPeakService.getTotalAlcoholAmount(records);
        int ans = 4500;
        assertEquals(ans, totalAmount);

        int totalAmountEqualsZero = BalmerPeakService.getTotalAlcoholAmount(new ArrayList<>());
        assertEquals(0, totalAmountEqualsZero);
    }

    private List<BalmerPeakRecord> getRecords() {
        List<BalmerPeakRecord> records = new ArrayList<>();
        records.add(new BalmerPeakRecord(AlcoholType.Beer, 1000, false));
        records.add(new BalmerPeakRecord(AlcoholType.Vodka, 400, true));
        records.add(new BalmerPeakRecord(AlcoholType.Beer, 1000, false));
        records.add(new BalmerPeakRecord(AlcoholType.Rum, 400, true));
        records.add(new BalmerPeakRecord(AlcoholType.Wine, 1000, false));
        records.add(new BalmerPeakRecord(AlcoholType.Whiskey, 700, true));
        return records;
    }
}