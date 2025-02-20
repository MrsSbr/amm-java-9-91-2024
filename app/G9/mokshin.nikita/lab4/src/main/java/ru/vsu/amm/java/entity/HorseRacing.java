package ru.vsu.amm.java.entity;

import java.time.LocalDate;


public class HorseRacing {
    private LocalDate date;
    private String firstPlaceNameHorse;
    private String secondPlaceNameHorse;
    private String thirdPlaceNameHorse;

    public HorseRacing(LocalDate date, String firstPlaceName, String secondPlaceName, String thirdPlaceName) {
        this.date = date;
        this.firstPlaceNameHorse = firstPlaceName;
        this.secondPlaceNameHorse = secondPlaceName;
        this.thirdPlaceNameHorse = thirdPlaceName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFirstPlaceNameHorse() {
        return firstPlaceNameHorse;
    }

    public void setFirstPlaceNameHorse(String firstPlaceNameHorse) {
        this.firstPlaceNameHorse = firstPlaceNameHorse;
    }

    public String getSecondPlaceNameHorse() {
        return secondPlaceNameHorse;
    }

    public void setSecondPlaceNameHorse(String secondPlaceNameHorse) {
        this.secondPlaceNameHorse = secondPlaceNameHorse;
    }

    public String getThirdPlaceNameHorse() {
        return thirdPlaceNameHorse;
    }

    public void setThirdPlaceNameHorse(String thirdPlaceNameHorse) {
        this.thirdPlaceNameHorse = thirdPlaceNameHorse;
    }
}
