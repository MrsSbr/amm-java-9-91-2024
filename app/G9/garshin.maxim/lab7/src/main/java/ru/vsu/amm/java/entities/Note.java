package ru.vsu.amm.java.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Note {
    private long noteId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long userId;
    private long categoryId;

    public Note(long noteId, String content, LocalDateTime createdAt, LocalDateTime updatedAt, long userId, long categoryId) {
        this.noteId = noteId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}