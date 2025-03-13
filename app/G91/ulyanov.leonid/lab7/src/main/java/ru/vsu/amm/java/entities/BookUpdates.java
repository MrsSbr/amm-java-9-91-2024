package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.amm.java.enums.UpdateType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookUpdates {
    @Setter
    private Integer updateId;
    @Setter
    private Integer userId;
    @Setter
    private Integer bookId;
    private LocalDateTime updateTime;
    @Setter
    private UpdateType updateType;

    public BookUpdates() {}

    public void setUpdateTime(LocalDateTime updateTime) {
        if (updateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Update time is after now");
        }
        this.updateTime = updateTime;
    }
}
