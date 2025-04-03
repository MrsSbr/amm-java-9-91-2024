package ru.vsu.amm.java.Entities;
import ru.vsu.amm.java.Enums.Roles;

import java.time.LocalDate;

public class UserEntity {
    private Long userID;
    private String userName;
    private String userPassword;
    private Roles userRole;
    private String phone;
    private LocalDate birthDate;


    public UserEntity() {}

    public UserEntity(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public UserEntity(Long userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public UserEntity(Long userID, String userName, String userPassword, Roles userRole, String phone, LocalDate birthDate) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Roles getUserRole() {
        return userRole;
    }

    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
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

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_ID=" + userID +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole=" + userRole +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
