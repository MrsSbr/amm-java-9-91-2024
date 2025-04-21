package ru.vsu.amm.java.Entities;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String passwordHash;

    public Patient(int id, String fullName, LocalDate birthdayDate, String phoneNumber, String email, String passwordHash) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthdayDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthdayDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}