package ru.vsu.amm.java.entity;

public class UserEntity {
    private Long id;
    private String login;
    private String nickname;
    private String phoneNumber;
    private String passwordHash;
    private String email;

    public UserEntity(Long id, String login, String nickname, String phoneNumber, String passwordHash, String email) {
        this.id = id;
        this.login = login;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }
}
