package ru.vsu.amm.java.sports.medals;

public class Medal {
    private Country country;
    private KindOfSport kindOfSport;
    private String athlete;
    private int place;

    public Medal(Country country, KindOfSport kindOfSport, String athlete, int place) {
        this.country = country;
        this.kindOfSport = kindOfSport;
        this.athlete = athlete;
        this.place = place;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public KindOfSport getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(KindOfSport kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public String getAthlete() {
        return athlete;
    }

    public void setAthlete(String athlete) {
        this.athlete = athlete;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return country + ";" + kindOfSport + ";" + athlete + ";" + place;
    }
}
