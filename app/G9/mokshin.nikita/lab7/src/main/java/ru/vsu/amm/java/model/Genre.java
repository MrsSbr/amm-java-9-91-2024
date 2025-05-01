package ru.vsu.amm.java.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Genre {
    private Long id;
    private String title;

    public Genre(String title) {
        this.title = title;
    }
}
