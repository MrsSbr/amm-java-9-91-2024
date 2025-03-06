package ru.vsu.amm.java.entities;

public class User {
    private Integer userId;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;

    public User() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        if (!lastName.matches("^[A-Z][a-z'.]+$")) {
            throw new IllegalArgumentException("Invalid last name");
        }
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        if (!firstName.matches("^[A-Z][a-z'.]+$")) {
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName;
    }

    public void setPatronymic(String patronymic) {
        if (!patronymic.matches("^[A-Z][a-z'.]+$")) {
            throw new IllegalArgumentException("Invalid patronymic name");
        }
        this.patronymic = patronymic;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^7[0-9]{10}$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
