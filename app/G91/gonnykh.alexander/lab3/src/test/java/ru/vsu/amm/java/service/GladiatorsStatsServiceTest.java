package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.battlerecord.BattleRecord;
import ru.vsu.amm.java.enums.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GladiatorsStatsServiceTest {
    private List<BattleRecord> battleRecords;

    @BeforeEach
    void setUp() {
        battleRecords = new ArrayList<>();
    }

    @Test
    @DisplayName("get deadliest animal (1)")
    void getDeadliestAnimalOne() {
        for (int i = 0; i < 4; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", "Zxc", Animal.Wolf, false, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 2),
                "Alex", "Zxc", Animal.Bull, false, false));

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 2),
                "Alex", "Zxc", Animal.Lion, true, false));

        List<Animal> expected = List.of(Animal.Wolf);
        List<Animal> received = GladiatorsStatsService.getDeadliestAnimal(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get deadliest animal (many)")
    void getDeadliestAnimalMany() {
        for (int i = 0; i < 4; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", "Zxc", Animal.Wolf, false, false));

            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", "Zxc", Animal.Bull, false, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 2),
                "Alex", "Zxc", Animal.Lion, true, false));

        List<Animal> expected = List.of(Animal.Bull, Animal.Wolf);
        List<Animal> received = GladiatorsStatsService.getDeadliestAnimal(battleRecords);
        assertEquals(expected, received);
    }


    @Test
    @DisplayName("get gladiators not from ludus")
    void getGladiatorsNotFromLudus() {

        for (int i = 0; i < 4; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", null, Animal.Wolf, true, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 6),
                "Alex", null, Animal.Lion, false, false));
        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 2),
                "Anton", "Zxc", Animal.Lion, false, false));

        List<String> expected = List.of("Alex");
        List<String> received = GladiatorsStatsService.getGladiatorsNotFromLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get gladiators not from ludus (empty)")
    void getGladiatorsNotFromLudusEmpty() {
        for (int i = 0; i < 5; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", null, Animal.Wolf, true, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 2),
                "Anton", "Zxc", Animal.Lion, false, false));

        List<String> expected = new ArrayList<>();
        List<String> received = GladiatorsStatsService.getGladiatorsNotFromLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get gladiators not from ludus (many)")
    void getGladiatorsNotFromLudusMany() {
        for (int i = 0; i < 4; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", null, Animal.Wolf, true, false));

            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Anton", null, Animal.Tiger, true, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 12),
                "Alex", null, Animal.Wolf, false, false));

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 12),
                "Anton", null, Animal.Tiger, false, false));

        List<String> expected = List.of("Anton", "Alex");
        List<String> received = GladiatorsStatsService.getGladiatorsNotFromLudus(battleRecords);
        assertEquals(expected, received);
    }


    @Test
    @DisplayName("get best ludus (one)")
    void getBestLudusOne() {
        for (int i = 0; i < 4; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", "Zxc", Animal.Wolf, true, false));

            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Anton", null, Animal.Tiger, true, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 12),
                "Alex", "Aboba", Animal.Wolf, true, false));

        List<String> expected = List.of("Zxc");
        List<String> received = GladiatorsStatsService.getBestLudus(battleRecords);
        assertEquals(expected, received);
    }


    @Test
    @DisplayName("get best ludus (many)")
    void getBestLudusMany() {
        for (int i = 0; i < 4; ++i) {
            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Alex", "Zxc", Animal.Wolf, true, false));

            battleRecords.add(new BattleRecord(LocalDate.of(123, 12, i + 1),
                    "Anton", "Aboba", Animal.Tiger, true, false));
        }

        battleRecords.add(new BattleRecord(LocalDate.of(123, 12, 12),
                "Alex", "Qwerty", Animal.Wolf, true, false));

        List<String> expected = List.of("Zxc", "Aboba");
        List<String> received = GladiatorsStatsService.getBestLudus(battleRecords);
        assertEquals(expected, received);
    }
}