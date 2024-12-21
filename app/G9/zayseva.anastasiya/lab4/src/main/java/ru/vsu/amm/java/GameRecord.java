package ru.vsu.amm.java;

import java.time.LocalDate;

public class GameRecord {
    private String title;
    private Genre genre;
    private LocalDate completionDate;
    private int hoursSpent;
    private int rating;

    public GameRecord(String title, Genre genre, LocalDate completionDate, int hoursSpent, int rating) {
        this.title = title;
        this.genre = genre;
        this.completionDate = completionDate;
        this.rating = rating;
        this.hoursSpent = hoursSpent;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public int getRating() {
        return rating;
    }

    public int getHoursSpent() {
        return hoursSpent;
    }
}
