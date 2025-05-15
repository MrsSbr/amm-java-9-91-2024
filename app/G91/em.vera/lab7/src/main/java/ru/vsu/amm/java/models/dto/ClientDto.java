package ru.vsu.amm.java.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClientDto {
    private Long idClient;
    private String email;
    private String name;
    private String password;
}
