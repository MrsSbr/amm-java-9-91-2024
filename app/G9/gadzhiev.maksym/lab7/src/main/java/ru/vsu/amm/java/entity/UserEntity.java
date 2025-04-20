package ru.vsu.amm.java.entity;

public class UserEntity {
    private Long id;
    private String login;
    private String password;
    private String number;
    private String email;

    public UserEntity(Long id, String login, String password, String number, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.number = number;
        this.email = email;
    }

    public UserEntity(String login, String password, String email, String number) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.number = number;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setPassword(String s) {
        this.password = s;
    }
}
