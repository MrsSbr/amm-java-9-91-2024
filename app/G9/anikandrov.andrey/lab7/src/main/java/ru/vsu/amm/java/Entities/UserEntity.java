package ru.vsu.amm.java.Entities;
import ru.vsu.amm.java.Enums.Roles;

import java.util.Date;

public class UserEntity {
    private Long userID;
    private String userName;
    private String password;
    private Roles role;
    private String phone;
    private Date birthDate;


    public UserEntity() {}

    public UserEntity(Long userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public UserEntity(Long userID, String userName, String password, Roles role, String phone, Date birthDate) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.birthDate = birthDate;
    }


    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (phone != null && phone.matches("^[0-9]{11}$")) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("setPhone Error");
        }
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
