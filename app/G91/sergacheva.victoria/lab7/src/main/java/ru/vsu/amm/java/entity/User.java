package ru.vsu.amm.java.entity;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String login;
    private String hashPassword;

    public User(Long id, String name, String login, String hashPassword) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.hashPassword = hashPassword;
    }

    public User(String name, String login, String hashPassword) {
        this.name = name;
        this.login = login;
        this.hashPassword = hashPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(login, user.login) && Objects.equals(hashPassword, user.hashPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, hashPassword);
    }
}