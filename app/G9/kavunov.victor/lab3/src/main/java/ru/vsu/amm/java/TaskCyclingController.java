package ru.vsu.amm.java;

import java.util.List;

public class TaskCyclingController {
    private final static int NUM_OF_RECORDS = 12427;

    public static void main(String[] args) {
        List<RaceResult> raceResults = RaceResultFactory.generateRandomList(NUM_OF_RECORDS);

        System.out.println("Номера участников, ставших призёрами за последние 3 года");
        System.out.println(RaceResultService.getLastThreeYearsPrizewinners(raceResults));
        System.out.println("Кол-во участников, ставших победителями");
        System.out.println(RaceResultService.getWinnersCount(raceResults));
        System.out.println("Номера участников, участвующих на протяжении 6 лет и ставших призёрами только в этом году");
        System.out.println(RaceResultService.getNewPrizewinnersLastParticipant(raceResults));
    }
}