package ru.vsu.amm.java;

import java.util.Objects;

abstract class AbstractMovie implements Movie {

    private String title;
    private int duration;

    public AbstractMovie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Duration: " + duration + " mins";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractMovie movie = (AbstractMovie) obj;
        return duration == movie.duration && title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration);
    }

    public String getDescription() {
        return "This is a movie.";
    }
}