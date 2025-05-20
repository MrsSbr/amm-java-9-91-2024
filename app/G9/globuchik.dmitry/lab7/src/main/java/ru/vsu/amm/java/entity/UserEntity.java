package ru.vsu.amm.java.entity;

public class UserEntity {
    private Long id;
    private String login;
    private String nickname;
    private String phoneNumber;
    private String passwordHash;
    private String email;
    private String salt;


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

    public void setId(long id) {
        this.id = id;
    }
}
