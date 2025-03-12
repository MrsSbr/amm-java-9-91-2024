package ru.vsu.amm.java.entity;

public class UserEntity {

    private Long id;

    private String hashPassword;

    private String email;

    public UserEntity() {

    }

    public UserEntity(Long id, String email, String hashPassword) {
        this.id = id;
        this.email = email;
        this.hashPassword = hashPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashPassword() {
        return hashPassword;
    }


    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
