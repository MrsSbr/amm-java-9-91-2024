package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.Role;

import java.util.Date;

public class User {
    private long userId;
    private String surname;
    private String name;
    private String patronymicname;
    private Date birthday;
    private String email;
    private String phoneNumber;
    private Role role;

    public User() {}

    public User(long userId, String surname, String name, String patronymicname, Date birthday,
                String email, String phoneNumber, Role role) {
        this.userId = userId;
        this.surname = surname;
        this.name = name;
        this.patronymicname = patronymicname;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPatronymicname() { return patronymicname; }
    public void setPatronymicname() {this.patronymicname = patronymicname; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
