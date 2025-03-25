package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.units.Difficulty;

import java.util.List;

public class Card {
    private Long id;
    private String topic;
    private List<String> words;
    private Difficulty difficulty;
    private Long actionId;
}
