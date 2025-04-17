package ru.vsu.amm.java.domain.entities;

public class Player {
    private Long id;
    private String login;
    private String password;
    private String email;

    public Player(Long id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public Player(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() { return password; }

    public String getEmail() {
        return email;
    }
}
