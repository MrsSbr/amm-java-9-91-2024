package ru.vsu.amm.java.Entities;
import ru.vsu.amm.java.Enums.Roles;

import java.util.Date;

public class User {
    private long userID;
    private String name;

    private String password;
    private Roles role;
    private String phone;
    private Date birthDate;


    public User () {}

    public User(long userID, String name, Roles role, String phone, Date birthDate) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
