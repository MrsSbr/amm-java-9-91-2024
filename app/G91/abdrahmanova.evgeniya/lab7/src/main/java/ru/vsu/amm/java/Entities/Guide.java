package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.Language;

public class Guide {
    private int id;
    private String fullName;
    private String bio;
    private double rating;
    private Language languages;
    private String eMail;
    private String numberPhone;

    public Guide(int id, String fullName, String bio, double rating, Language languages, String eMail, String numberPhone) {
        this.id = id;
        this.fullName = fullName;
        this.bio = bio;
        this.rating = rating;
        this.languages = languages;
        this.eMail = eMail;
        this.numberPhone = numberPhone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public Language getLanguages() { return languages; }
    public void setLanguages(Language languages) { this.languages = languages; }

    public String getEMail() { return eMail; }
    public void setEMail(String eMail) { this.eMail = eMail; }

    public String getNumberPhone() { return numberPhone; }
    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }
}
