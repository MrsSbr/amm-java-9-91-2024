package ru.vsu.amm.java.Entities;

import java.time.LocalDate;

public class User {
    private int id;
    private String fullName;
    private LocalDate birthday;
    private String eMail;
    private String numberPhone;

    public User(int id, String fullName, LocalDate birthday, String eMail, String numberPhone) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.eMail = eMail;
        this.numberPhone = numberPhone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) {
        LocalDate earliestAllowed = LocalDate.of(1884, 1, 1);
        if (birthday.isBefore(earliestAllowed)) {
            throw new IllegalArgumentException("invalid birthday!");
        }
        this.birthday = birthday; }

    public String geteMail() { return eMail; }
    public void seteMail(String eMail) { this.eMail = eMail; }

    public String getNumberPhone() { return numberPhone; }
    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }
}
