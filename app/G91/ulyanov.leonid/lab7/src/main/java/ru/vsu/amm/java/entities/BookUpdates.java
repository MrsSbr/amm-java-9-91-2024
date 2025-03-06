package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.UpdateType;
import java.time.LocalDateTime;

public class BookUpdates {
    private Integer updateId;
    private Integer userId;
    private Integer bookId;
    private LocalDateTime updateTime;
    private UpdateType updateType;

    public BookUpdates() {}

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        if (updateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Update time is after now");
        }
        this.updateTime = updateTime;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }
}
