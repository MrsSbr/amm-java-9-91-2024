package ru.vsu.amm.java;

import java.util.Objects;

class ActionMovie extends AbstractMovie {

    private int explosionsCount;

    public ActionMovie(String title, int duration, int explosionsCount) {

        super(title, duration);
        this.explosionsCount = explosionsCount;
    }

    public int getExplosionsCount() {
        return explosionsCount;
    }

    @Override
    public String getGenre() {
        return "Action";
    }

    @Override
    public String getDescription() {
        return "This is an action-packed movie!";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        ActionMovie actionMovie = (ActionMovie) obj;
        return explosionsCount == actionMovie.explosionsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), explosionsCount);
    }

    @Override
    public String toString() {
        return super.toString() + ", Genre: " + getGenre() + ", Explosions: " + explosionsCount;
    }
}
