package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.Role;

import java.time.LocalDate;

public class User {
    private int id;
    private String password;
    private String fullName;
    private LocalDate birthday;
    private String eMail;
    private String numberPhone;
    private Role userRole;

    public User(int id, String password, String fullName, LocalDate birthday, String eMail, String numberPhone, Role userRole) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
        this.eMail = eMail;
        this.numberPhone = numberPhone;
        this.userRole = userRole;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) {
        LocalDate earliestAllowed = LocalDate.of(1884, 1, 1);
        if (birthday.isBefore(earliestAllowed)) {
            throw new IllegalArgumentException("invalid birthday!");
        }
        this.birthday = birthday; }

    public String geteMail() { return eMail; }
    public void seteMail(String eMail) { this.eMail = eMail; }

    public String getNumberPhone() { return numberPhone; }
    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }

    public Role getUserRole() {
        return userRole;
    }
    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}
