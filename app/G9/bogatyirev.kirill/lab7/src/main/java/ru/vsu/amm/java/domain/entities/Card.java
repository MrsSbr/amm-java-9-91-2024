package ru.vsu.amm.java.domain.entities;



import ru.vsu.amm.java.data.entities.utils.Difficulty;
import ru.vsu.amm.java.data.entities.utils.Topic;

import java.util.List;

public class Card {
    private Long id;
    private Topic topic;
    private List<WordToAction> wordsToActions;
    private Difficulty difficulty;

    public Card(Long id, Topic topic, List<WordToAction> wordsToAction, Difficulty difficulty) {
        this.id = id;
        this.topic = topic;
        this.wordsToActions = wordsToAction;
        this.difficulty = difficulty;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setWordsToActions(List<WordToAction> wordsToActions) {
        this.wordsToActions = wordsToActions;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<WordToAction> getWordsToActions() {
        return wordsToActions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
