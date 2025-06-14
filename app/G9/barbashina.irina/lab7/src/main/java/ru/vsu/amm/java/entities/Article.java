package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {
    private Long id;
    private String title;
    private String content;
    private LocalDate datePublication;
    private Category category;
    private Author author;

    public void setDatePublication(LocalDate datePublication) {
        LocalDate currentDate = LocalDate.now();
        if (datePublication != null && datePublication.isAfter(currentDate)) {
            throw new IllegalArgumentException("Publication date cannot be in the future");
        }
        this.datePublication = datePublication;
    }
}