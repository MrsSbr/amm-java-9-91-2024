package ru.vsu.amm.java;

import java.util.*;


public class GameRecord {
    private Date dateOfSell;
    private String name;
    private Genre genre;
    private int price;

    GameRecord(){};

    GameRecord(Date data, String name, Genre genre, int price){
        dateOfSell = data;
        this.name = name;
        this.genre = genre;
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }
}
