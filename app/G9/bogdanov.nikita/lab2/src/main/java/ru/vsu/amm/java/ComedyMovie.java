package ru.vsu.amm.java;

import java.util.Objects;

class ComedyMovie extends AbstractMovie{

    private String mainComedian;

    public ComedyMovie(String title, int duration, String mainComedian) {
        super(title, duration);
        this.mainComedian = mainComedian;
    }

    public String getMainComedian() {
        return mainComedian;
    }

    @Override
    public String getGenre() {
        return "Comedy";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        ComedyMovie comedyMovie = (ComedyMovie) obj;
        return mainComedian.equals(comedyMovie.mainComedian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mainComedian);
    }

    @Override
    public String toString() {
        return super.toString() + ", Genre: " + getGenre() + ", Main Comedian: " + mainComedian;
    }
}