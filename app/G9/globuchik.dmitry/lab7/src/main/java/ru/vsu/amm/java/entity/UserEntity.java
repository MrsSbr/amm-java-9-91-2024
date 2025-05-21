package ru.vsu.amm.java.entity;

public class UserEntity {
    private final String login;
    private final String nickname;
    private final String phoneNumber;
    private final String passwordHash;
    private final String email;
    private final String salt;
    private int loginCount;
    private Long id;

    public UserEntity(Long id, String login, String nickname, String phoneNumber, String passwordHash, String email, String salt) {
        this.id = id;
        this.login = login;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.email = email;
        this.salt = salt;
    }

    public UserEntity(Long id, String login, String nickname, String phoneNumber, String passwordHash, String email, String salt, int loginCount) {
        this.id = id;
        this.login = login;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.email = email;
        this.salt = salt;
        this.loginCount = loginCount;
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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getSalt() {
        return salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
