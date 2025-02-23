package ru.vsu.amm.java.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private long userID;
    private String password;
    private String phoneNumber;
    private String email;
    private String login;

    public User() {}

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("\\+7\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    private boolean isValidEmail(String email) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}