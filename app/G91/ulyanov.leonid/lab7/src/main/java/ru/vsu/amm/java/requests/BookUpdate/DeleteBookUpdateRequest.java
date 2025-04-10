package ru.vsu.amm.java.requests.BookUpdate;

import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;

import java.time.LocalDateTime;

public record DeleteBookUpdateRequest(Integer userId,
                                      Integer bookId,
                                      LocalDateTime updateTime,
                                      UpdateType updateType) {
}
