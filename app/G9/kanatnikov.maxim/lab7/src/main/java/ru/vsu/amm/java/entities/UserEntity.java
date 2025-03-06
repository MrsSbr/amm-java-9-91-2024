package ru.vsu.amm.java.entities;

public class UserEntity {
    private int userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String city;
    private String email;
    private String phoneNumber;
    private String passwordHash;

    public UserEntity() {
    }

    public UserEntity(int userId, String firstName, String lastName, String patronymic,
                      String city, String email, String phoneNumber, String passwordHash) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
