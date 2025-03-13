package ru.vsu.amm.java.Entities;
import ru.vsu.amm.java.Enums.Roles;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UserEntity {
    private Long userID;
    private String userName;
    private String password;
    private Roles role;
    private String phone;
    private LocalDate birthDate;


    public UserEntity() {}

    public UserEntity(Long userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public UserEntity(Long userID, String userName, String password, Roles role, String phone, LocalDate birthDate) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public UserEntity(Long userID, String userName, String password, Roles role, String phone, Date birthDate) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.birthDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
