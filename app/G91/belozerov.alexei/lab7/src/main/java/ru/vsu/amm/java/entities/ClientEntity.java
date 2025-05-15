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
}
