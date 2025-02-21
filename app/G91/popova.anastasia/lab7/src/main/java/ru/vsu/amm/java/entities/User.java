package ru.vsu.amm.java.entities;

import java.util.UUID;

/*
*   если я хочу проверять допустимые символы для name и password
*   при попытке присвоения нового значения (через регулярки),
*   мне стоит задать это прям в сеттере или всё-таки где-то в другом месте?
 */

public class User {

    private UUID userID;
    private String name;
    private String email;
    private String passwordHash;

    public User() {};

    public UUID getUserID() {
        return userID;
    }
    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
