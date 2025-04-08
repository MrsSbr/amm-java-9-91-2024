package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.Role;

public class UserEntity {
    private long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String city;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;

    public UserEntity() {
    }

    public UserEntity(long userId, String firstName, String lastName, String patronymic,
                      String city, String email, String phoneNumber, String password, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ru.vsu.amm.java.enums.Role getRole() {
        return role;
    }

    public void setRole(ru.vsu.amm.java.enums.Role role) {
        this.role = role;
    }
}
