package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private long noteId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long userId;
    private long categoryId;
}