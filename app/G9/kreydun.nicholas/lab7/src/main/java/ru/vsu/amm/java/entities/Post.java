package ru.vsu.amm.java.entities;

import java.time.LocalTime;
import java.util.UUID;

public class Post {

    private UUID id;
    private UUID userId;
    private String content;
    private LocalTime createdAt;

    public Post() {}

    public UUID getId() {
        return id;
    }
    public UUID getUserId() {
        return userId;
    }
    public String getContent() {
        return content;
    }
    public LocalTime getCreatedAt() {
        return createdAt;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }
}
