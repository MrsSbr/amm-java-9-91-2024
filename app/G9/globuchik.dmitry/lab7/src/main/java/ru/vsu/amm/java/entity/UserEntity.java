package ru.vsu.amm.java.entity;

public class UserEntity {
    private Long id;
    private final String login;
    private final String nickname;
    private final String phoneNumber;
    private final String passwordHash;
    private final String email;
    private final String salt;


    public UserEntity(Long id, String login, String nickname, String phoneNumber, String passwordHash, String email, String salt) {
        this.id = id;
        this.login = login;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.email = email;
        this.salt = salt;
    }

    public UserEntity(String login, String nickname, String phoneNumber, String passwordHash, String email, String salt) {
        this.id = null;
        this.login = login;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.email = email;
        this.salt = salt;
    }


    public String getSalt() {
        return salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
