package ru.vsu.amm.java.main;

import ru.vsu.amm.java.main.Genre;

import java.time.LocalDate;


public class GameRecord {
    private LocalDate dateOfSell;
    private String name;
    private Genre genre;
    private Integer price;

    public GameRecord() {
    };

    public GameRecord(LocalDate data, String name, Genre genre, int price) {
        dateOfSell = data;
        this.name = name;
        this.genre = genre;
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getDate() {
        return dateOfSell;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ' ' + genre.toString() + price;
    }
}
