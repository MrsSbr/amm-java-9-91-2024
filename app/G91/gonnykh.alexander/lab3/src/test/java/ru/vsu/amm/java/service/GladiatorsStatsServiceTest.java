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
    private static final BattleRecord BATTLE_ALEX_BULL_WIN =
            new BattleRecord(LocalDate.of(123, 12, 2), "Alex", "Zxc", Animal.Bull, true, true);

    private static final BattleRecord BATTLE_ALEX_LION_WIN =
            new BattleRecord(LocalDate.of(123, 12, 2), "Alex", "Zxc", Animal.Lion, true, true);

    private static final BattleRecord BATTLE_ALEX_WOLF_LOSE =
            new BattleRecord(LocalDate.of(123, 12, 1), "Alex", "Zxc", Animal.Wolf, false, false);

    private static final BattleRecord BATTLE_ALEX_WOLF_WIN_NOT_LUDUS =
            new BattleRecord(LocalDate.of(123, 12, 1), "Alex", null, Animal.Wolf, true, false);

    private static final BattleRecord BATTLE_ALEX_BULL_LOSE = new BattleRecord(
            LocalDate.of(123, 12, 2), "Alex", "Zxc", Animal.Bull, false, false);

    private static final BattleRecord BATTLE_ALEX_LION_LOSE_NO_LUDUS = new BattleRecord(
            LocalDate.of(123, 12, 6), "Alex", null, Animal.Lion, false, false);

    private static final BattleRecord BATTLE_ANTON_LION_LOSE_NO_LUDUS = new BattleRecord(
            LocalDate.of(123, 12, 2), "Anton", null, Animal.Lion, false, false);

    private static final BattleRecord BATTLE_ANTON_LION_WIN_NO_LUDUS = new BattleRecord(
            LocalDate.of(123, 12, 2), "Anton", null, Animal.Lion, true, false);

    private static final BattleRecord BATTLE_ANTON_LION_WIN = new BattleRecord(
            LocalDate.of(123, 12, 2), "Anton", "Aboba", Animal.Lion, true, false);

    private List<BattleRecord> battleRecords;

    @BeforeEach
    void setUp() {
        battleRecords = new ArrayList<>();
    }

    @Test
    @DisplayName("get deadliest animal (empty)")
    void getDeadliestAnimalEmpty() {
        battleRecords.add(BATTLE_ALEX_BULL_WIN);
        battleRecords.add(BATTLE_ALEX_LION_WIN);

        List<Animal> expected = List.of();
        List<Animal> received = GladiatorsStatsService.getDeadliestAnimal(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get deadliest animal (one)")
    void getDeadliestAnimalOne() {
        battleRecords.add(BATTLE_ALEX_WOLF_LOSE);
        battleRecords.add(BATTLE_ANTON_LION_WIN);

        List<Animal> expected = List.of(Animal.Wolf);
        List<Animal> received = GladiatorsStatsService.getDeadliestAnimal(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get deadliest animal (many)")
    void getDeadliestAnimalMany() {
        battleRecords.add(BATTLE_ALEX_WOLF_LOSE);
        battleRecords.add(BATTLE_ALEX_BULL_LOSE);
        battleRecords.add(BATTLE_ALEX_LION_WIN);

        List<Animal> expected = List.of(Animal.Wolf, Animal.Bull);
        List<Animal> received = GladiatorsStatsService.getDeadliestAnimal(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get gladiators not from ludus (empty)")
    void getGladiatorsNotFromLudusEmpty() {
        battleRecords.add(BATTLE_ALEX_WOLF_WIN_NOT_LUDUS);
        battleRecords.add(BATTLE_ALEX_BULL_WIN);

        List<String> expected = new ArrayList<>();
        List<String> received = GladiatorsStatsService.getGladiatorsNotFromLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get gladiators not from ludus (one)")
    void getGladiatorsNotFromLudus() {
        for (int i = 0; i < 3; ++i) {
            battleRecords.add(BATTLE_ALEX_WOLF_WIN_NOT_LUDUS);
        }

        battleRecords.add(BATTLE_ALEX_LION_LOSE_NO_LUDUS);
        battleRecords.add(BATTLE_ANTON_LION_LOSE_NO_LUDUS);

        List<String> expected = List.of("Alex");
        List<String> received = GladiatorsStatsService.getGladiatorsNotFromLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get gladiators not from ludus (many)")
    void getGladiatorsNotFromLudusMany() {
        for (int i = 0; i < 3; ++i) {
            battleRecords.add(BATTLE_ALEX_WOLF_WIN_NOT_LUDUS);
            battleRecords.add(BATTLE_ANTON_LION_WIN_NO_LUDUS);
        }

        battleRecords.add(BATTLE_ALEX_LION_LOSE_NO_LUDUS);
        battleRecords.add(BATTLE_ANTON_LION_LOSE_NO_LUDUS);
        List<String> expected = List.of("Anton", "Alex");
        List<String> received = GladiatorsStatsService.getGladiatorsNotFromLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get best ludus (empty)")
    void getBestLudusEmpty() {
        battleRecords.add(BATTLE_ALEX_WOLF_WIN_NOT_LUDUS);
        battleRecords.add(BATTLE_ANTON_LION_WIN_NO_LUDUS);

        List<String> expected = new ArrayList<>();
        List<String> received = GladiatorsStatsService.getBestLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get best ludus (one)")
    void getBestLudusOne() {
        battleRecords.add(BATTLE_ALEX_BULL_WIN);
        battleRecords.add(BATTLE_ANTON_LION_WIN);
        battleRecords.add(BATTLE_ALEX_LION_WIN);

        List<String> expected = List.of("Zxc");
        List<String> received = GladiatorsStatsService.getBestLudus(battleRecords);
        assertEquals(expected, received);
    }

    @Test
    @DisplayName("get best ludus (many)")
    void getBestLudusMany() {
        battleRecords.add(BATTLE_ALEX_BULL_WIN);
        battleRecords.add(BATTLE_ANTON_LION_WIN);

        List<String> expected = List.of("Zxc", "Aboba");
        List<String> received = GladiatorsStatsService.getBestLudus(battleRecords);
        assertEquals(expected, received);
    }
}