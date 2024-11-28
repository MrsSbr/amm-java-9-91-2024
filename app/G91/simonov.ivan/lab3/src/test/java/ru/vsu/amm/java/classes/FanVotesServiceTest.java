package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.entity.FanVote;
import ru.vsu.amm.java.interfaces.Testable;
import ru.vsu.amm.java.service.FanVotesService;

import java.util.List;

public abstract class FanVotesServiceTest implements Testable {
    protected List<FanVote> fanVotes;
    protected final static int MIN_VOTE = FanVotesService.MIN_VOTE;
    protected final static int MAX_VOTE = FanVotesService.MAX_VOTE;
}
