package ru.vsu.amm.java;

import ru.vsu.amm.java.battlerecord.BattleRecord;
import ru.vsu.amm.java.service.GladiatorsStatsService;
import ru.vsu.amm.java.util.BattleRecordGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Введите число записей о сражениях: ");
            int numberOfRecords = in.nextInt();
            List<BattleRecord> records = BattleRecordGenerator.generateBattleRecords(numberOfRecords);

            System.out.println("Самое смертоносное животное:"
                    + GladiatorsStatsService.getDeadliestAnimal(records));

            System.out.println("""
                    Список гладиаторов не из лудуса, которые выжили не менее 3 раз,\s
                    но затем погибли в битве так и получив помилование:"""
                    + GladiatorsStatsService.getGladiatorsNotFromLudus(records));

            System.out.println("Лудус, который готовит лучших бойцов с животными:"
                    + GladiatorsStatsService.getBestLudus(records));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}