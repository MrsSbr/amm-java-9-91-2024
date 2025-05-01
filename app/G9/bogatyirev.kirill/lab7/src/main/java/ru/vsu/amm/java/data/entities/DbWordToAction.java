package ru.vsu.amm.java.data.entities;

public class DbWordToAction {
    private Long id;
    private String word;
    private Long actionId;
    private Long cardId;

    public DbWordToAction(Long id, String word, Long actionId, Long cardId) {
        this.id = id;
        this.word = word;
        this.actionId = actionId;
        this.cardId = cardId;
    }

    public DbWordToAction(String word, Long actionId, Long cardId) {
        this.word = word;
        this.actionId = actionId;
        this.cardId = cardId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public Long getActionId() {
        return actionId;
    }

    public Long getCardId() {
        return cardId;
    }
}
