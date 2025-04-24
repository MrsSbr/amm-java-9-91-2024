package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.UserRole;

public class User {
    private int userId;
    private String username;
    private String userPassword;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;

    // Конструкторы
    public User() {}

    public User(String username, String userPassword, String email,
                String firstName, String lastName, UserRole userRole) {
        this.username = username;
        this.userPassword = userPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
    }

    // Геттеры и сеттеры
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public UserRole getUserRole() { return userRole; }
    public void setUserRole(UserRole userRole) { this.userRole = userRole; }

}