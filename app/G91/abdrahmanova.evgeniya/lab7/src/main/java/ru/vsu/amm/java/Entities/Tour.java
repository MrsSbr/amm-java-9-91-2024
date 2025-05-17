package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.Language;

public class Tour {
    private int id;
    private int idGuide;
    private String title;
    private String description;
    private double duration;
    private int price;
    private int maxParticipants;
    private String startLocation;
    private Language Languages;

    public Tour(int id, int idGuide, String title, String description, double duration, int price, int maxParticipants, String startLocation, Language Languages) {
        this.id = id;
        this.idGuide = idGuide;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.startLocation = startLocation;
        this.Languages = Languages;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdGuide() { return idGuide; }
    public void setIdGuide(int idGuide) { this.idGuide = idGuide; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public Language getLanguages() { return Languages; }
    public void setLanguages(Language Languages) { this.Languages = Languages; }
}
