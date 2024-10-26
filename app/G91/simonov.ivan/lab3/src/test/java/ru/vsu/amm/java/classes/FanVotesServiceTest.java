package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.interfaces.Testable;
import ru.vsu.amm.java.service.FanVotesService;

public abstract class FanVotesServiceTest implements Testable {
    protected FanVotesService fanVotesService;
    protected final static int MIN_VOTE = FanVotesService.getMinVote();
    protected final static int MAX_VOTE = FanVotesService.getMaxVote();

    public FanVotesServiceTest() {
        fanVotesService = new FanVotesService();
    }
}
