package ru.vsu.amm.java.requests.BookUpdate;

import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;

import java.time.LocalDateTime;

public record ChangeBookUpdateRequest (Integer userId,
                                      Integer bookId,
                                      DownloadBookRequest changeBookRequest,
                                      LocalDateTime updateTime,
                                      UpdateType updateType){
}
