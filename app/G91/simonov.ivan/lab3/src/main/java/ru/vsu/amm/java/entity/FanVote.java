package ru.vsu.amm.java.entity;

import java.util.Set;

public record FanVote(Set<Integer> votes) {

    public Set<Integer> getVotes() {
        return votes;
    }
}
