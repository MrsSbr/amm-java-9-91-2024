import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.*;
import java.util.stream.IntStream;

public class FanVotesServiceTest {

    private final List<FanVote> FAN_VOTES = fillFanVotes();
    private final int MIN_VOTE = FanVotesService.getMinVote();
    private final int MAX_VOTE = FanVotesService.getMaxVote();

    public List<FanVote> fillFanVotes() {
        return new ArrayList<>(List.of(
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(5, 6, 3)),
                new FanVote(Set.of(1, 2, 3)),
                new FanVote(Set.of(5, 2, 8)),
                new FanVote(Set.of(10, 6, 8))
        ));
    }

    @Test
    public void testFindMostPopularPlayersBasedOnFilledCollection() {
        Assertions.assertEquals(Set.of(2, 3), FanVotesService.findMostPopularPlayers(FAN_VOTES));
    }

    @Test
    public void testFindPlayersWithoutVotesBasedOnFilledCollection() {
        Set<Integer> expectedCollection = new HashSet<>(Set.of(4, 7, 9));
        expectedCollection.addAll(IntStream.rangeClosed(11, MAX_VOTE).boxed().toList());
        Assertions.assertEquals(expectedCollection, FanVotesService.findPlayersWithoutVotes(FAN_VOTES));
    }

    @Test
    public void testFindVotedPlayersBasedOnFilledCollection() {
        Assertions.assertEquals(Set.of(1, 2, 3, 5, 6, 8, 10), FanVotesService.findVotedPlayers(FAN_VOTES));
    }

    @Test
    public void testFindMostPopularPlayersBasedOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> FanVotesService.findMostPopularPlayers(null));
    }

    @Test
    public void testFindPlayersWithoutVotesBasedOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> FanVotesService.findPlayersWithoutVotes(null));
    }

    @Test
    public void testFindVotedPlayersBasedOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> FanVotesService.findVotedPlayers(null));
    }

    @Test
    public void testFindMostPopularPlayersBasedOnEmptyCollection() {
        Set<Integer> expectedCollection = new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE).boxed().toList());
        Assertions.assertEquals(expectedCollection, FanVotesService.findMostPopularPlayers(new ArrayList<>()));
    }

    @Test
    public void testFindPlayersWithoutVotesBasedOnEmptyCollection() {
        Set<Integer> expectedCollection = new HashSet<>(IntStream.rangeClosed(MIN_VOTE, MAX_VOTE).boxed().toList());
        Assertions.assertEquals(expectedCollection, FanVotesService.findPlayersWithoutVotes(new ArrayList<>()));
    }

    @Test
    public void testFindVotedPlayersBasedOnEmptyCollection() {
        Assertions.assertEquals(Set.of(), FanVotesService.findVotedPlayers(new ArrayList<>()));
    }
}
