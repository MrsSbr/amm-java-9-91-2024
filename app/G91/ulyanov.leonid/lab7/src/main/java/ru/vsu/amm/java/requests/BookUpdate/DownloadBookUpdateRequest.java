package ru.vsu.amm.java.requests.BookUpdate;

import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;

import java.time.LocalDateTime;

public record DownloadBookUpdateRequest(Integer userId,
                                        DownloadBookRequest downloadBookRequest,
                                        LocalDateTime updateTime,
                                        UpdateType updateType) {
}
