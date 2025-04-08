package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.Genre;

public class BoardGame {
    private Long boardGameId;
    private String name;
    private int price;
    private Genre genre;
    private int minAge;
    private String publisher;
    private String description;

    public BoardGame() {
    }

    public BoardGame(Long boardGameId, String name, int price, Genre genre,
                     int minAge, String publisher, String description) {
        this.boardGameId = boardGameId;
        this.name = name;
        this.price = price;
        this.genre = genre;
        this.minAge = minAge;
        this.publisher = publisher;
        this.description = description;
    }

    public Long getBoardGameId() {
        return boardGameId;
    }

    public void setBoardGameId(Long boardGameId) {
        this.boardGameId = boardGameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
