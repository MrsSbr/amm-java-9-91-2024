package ru.vsu.amm.java;

import java.util.Objects;

class DramaMovie extends AbstractMovie{

    private boolean basedOnTrueStory;

    public DramaMovie(String title, int duration, boolean basedOnTrueStory) {
        super(title, duration);
        this.basedOnTrueStory = basedOnTrueStory;
    }

    @Override
    public String getGenre() {
        return "Drama";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        DramaMovie dramaMovie = (DramaMovie) obj;
        return basedOnTrueStory == dramaMovie.basedOnTrueStory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), basedOnTrueStory);
    }

    @Override
    public String toString() {
        return super.toString() + ", Genre: " + getGenre() + ", Based on True Story: " + basedOnTrueStory;
    }
}