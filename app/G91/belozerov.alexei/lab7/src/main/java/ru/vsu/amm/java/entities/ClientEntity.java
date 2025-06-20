package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientEntity {
    private Long clientId;
    private String name;
    private String email;
    private String password;

    public ClientEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
