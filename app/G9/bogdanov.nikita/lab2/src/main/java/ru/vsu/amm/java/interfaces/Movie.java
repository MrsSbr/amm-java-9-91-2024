package ru.vsu.amm.java.interfaces;

import ru.vsu.amm.java.classes.Ganre;

public interface Movie {

    String getTitle();

    int getDuration();

    Ganre getGenre();
}