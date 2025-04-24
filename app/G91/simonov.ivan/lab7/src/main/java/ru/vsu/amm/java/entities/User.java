package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.Role;

import java.util.Objects;

public class User {

    private int userId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String login;
    private String password;
    private Role role;

    public User() {
    }

    public User(String lastName,
                String firstName,
                String patronymic,
                String login,
                String password,
                Role role) {

        this.role = role;
        this.password = password;
        this.login = login;
        this.patronymic = patronymic;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            this.login = login;
        } else {
            throw new IllegalArgumentException("Invalid login!");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(lastName, user.lastName) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role);

    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, patronymic, login, password, role);
    }

}
