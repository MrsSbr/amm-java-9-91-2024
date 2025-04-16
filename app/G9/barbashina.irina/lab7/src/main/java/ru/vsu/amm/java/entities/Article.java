package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {
    private Long id;
    private String title;
    private String content;
    private Date datePublication;
    private Category category;
    private Author author;

    public void setDatePublication(Date datePublication) {
        Date currentDate = new Date();
        if (datePublication != null && datePublication.after(currentDate)) {
            throw new IllegalArgumentException("Publication date cannot be in the future");
        }
        this.datePublication = datePublication;
    }

}
