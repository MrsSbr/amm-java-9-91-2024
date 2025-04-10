package ru.vsu.amm.java.requests;

import ru.vsu.amm.java.enums.UpdateType;

import java.time.LocalDateTime;

public record CreateBookUpdateRequest(Integer userId,
                                      CreateBookRequest createBookRequest,
                                      LocalDateTime updateTime,
                                      UpdateType updateType) {
}
