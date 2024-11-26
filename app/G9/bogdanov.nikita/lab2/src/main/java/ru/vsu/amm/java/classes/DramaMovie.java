package ru.vsu.amm.java.classes;

import java.util.Objects;

public class DramaMovie extends AbstractMovie {

    private boolean basedOnTrueStory;

    public DramaMovie(String title, int duration, boolean basedOnTrueStory) {
        super(title, duration);
        this.basedOnTrueStory = basedOnTrueStory;
    }

    @Override
    public Ganre getGenre() {
        return Ganre.Drama;
    }

    @Override
    public String getDescription() {
        return "This is an drama movie!";
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