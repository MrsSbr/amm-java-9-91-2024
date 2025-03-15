package ru.vsu.amm.java.entities;

public class UserEntity {
    private long userId;
    private String userNickname;
    private String email;
    private String userPassword;

    public UserEntity(long userId, String userNickname, String email, String userPassword) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.email = email;
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}