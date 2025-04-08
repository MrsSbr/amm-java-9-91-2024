package ru.vsu.amm.java.requests;

import lombok.Setter;
import ru.vsu.amm.java.enums.UpdateType;

import java.time.LocalDateTime;

public record AddBookUpdateRequest(Integer userId,
                                   Integer bookId,
                                   LocalDateTime updateTime,
                                   UpdateType updateType) {
}
