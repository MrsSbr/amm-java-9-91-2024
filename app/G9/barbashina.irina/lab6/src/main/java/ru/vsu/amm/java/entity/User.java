package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.annotation.Column;
import ru.vsu.amm.java.annotation.Id;
import ru.vsu.amm.java.annotation.JoinColumn;
import ru.vsu.amm.java.annotation.Table;

@Table(name = "users")
public class User {
    @Id
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "address_id", targetEntity = Address.class, targetColumn = "id")
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
