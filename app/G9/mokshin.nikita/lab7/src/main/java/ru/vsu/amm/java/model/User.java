package ru.vsu.amm.java.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String login;
    private String hash_password;
}
