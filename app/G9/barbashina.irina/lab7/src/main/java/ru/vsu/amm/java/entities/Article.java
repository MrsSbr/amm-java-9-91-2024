package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {
    private Long id;
    private String title;
    private String сontent;
    private java.sql.Date datePublication;
    private Сategory category;
    private Author author;

}
