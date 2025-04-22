package ru.vsu.amm.java.data.entities;

import ru.vsu.amm.java.data.entities.utils.Difficulty;

import java.util.List;

public class Card {
    private Long id;
    private String topic;
    private List<Long> wordsToActions;//может id word to action
    private Difficulty difficulty;

    public Card(Long id, String topic, List<Long> wordsToAction, Difficulty difficulty) {
        this.id = id;
        this.topic = topic;
        this.wordsToActions = wordsToAction;
        this.difficulty = difficulty;
    }

    public Card() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setWordsToActions(List<Long> wordsToActions) {
        this.wordsToActions = wordsToActions;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public List<Long> getWordsToActions() {
        return wordsToActions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
