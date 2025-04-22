package ru.vsu.amm.java.data.entities;


public class DbCard {
    private Long id;
    private String topic;
    private String difficulty;
    private Long playerId;

    public DbCard(Long id, String topic, String difficulty, Long playerId) {
        this.id = id;
        this.topic = topic;
        this.difficulty = difficulty;
        this.playerId = playerId;
    }

    public DbCard(String topic, String difficulty, Long playerId) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.playerId = playerId;
    }

    public DbCard() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Long getPlayerId() {
        return playerId;
    }
}
