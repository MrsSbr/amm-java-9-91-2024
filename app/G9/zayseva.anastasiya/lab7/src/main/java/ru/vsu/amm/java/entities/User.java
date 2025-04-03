package ru.vsu.amm.java.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private long userId;
    private String password;
    private String phoneNumber;
    private String email;
    private String login;
    private String username;

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format. Expected format: +7XXXXXXXXXX");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty() && !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^\\+7\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    private boolean isValidEmail(String email) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", phoneNumber=' " + phoneNumber + '\'' +
                ", email=' " + email + '\'' +
                ", login=' " + login + '\'' +
                ", username=' " + username + '\'' +

                '}';
    }
}
