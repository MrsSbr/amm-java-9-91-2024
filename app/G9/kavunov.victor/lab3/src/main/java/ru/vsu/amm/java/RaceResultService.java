package ru.vsu.amm.java;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.stream.Collectors;


public class RaceResultService {
    public static Set<Integer> getLastThreeYearsPrizewinners(List<RaceResult> raceResults) {
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);
        return raceResults.stream()
                .filter(raceResult -> raceResult.raceDate().isAfter(threeYearsAgo) && raceResult.place() <= 3)
                .map(RaceResult::participantNum)
                .collect(Collectors.toSet());
    }

    public static long getWinnersCount(List<RaceResult> raceResults) {
        return raceResults.stream()
                .filter(raceResult -> raceResult.place() == 1)
                .map(RaceResult::participantNum)
                .distinct()
                .count();
    }

    public static Set<Integer> getNewPrizewinnersLastParticipant(List<RaceResult> raceResults) {
        LocalDate yearAgo = LocalDate.now().minusYears(1);
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        LocalDate sixYearsAgo = LocalDate.now().minusYears(6);
        Set<Integer> thisYearPrizewinners = raceResults.stream()
                .filter(raceResult -> raceResult.raceDate().isAfter(yearAgo) && raceResult.place() <= 3)
                .map(RaceResult::participantNum)
                .collect(Collectors.toSet());
        Set<Integer> fiveYearsPrizewinners = raceResults.stream()
                .filter(raceResult -> raceResult.raceDate().isAfter(sixYearsAgo)
                        && raceResult.raceDate().isBefore(yearAgo) && raceResult.place() <= 3)
                .map(RaceResult::participantNum)
                .collect(Collectors.toSet());
        return raceResults.stream()
                .filter(raceResult -> raceResult.raceDate().isBefore(fiveYearsAgo) && raceResult.place() > 3)
                .map(RaceResult::participantNum)
                .filter(thisYearPrizewinners::contains)
                .filter(participantNum -> !(fiveYearsPrizewinners.contains(participantNum)))
                .collect(Collectors.toSet());
    }
}
