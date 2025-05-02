package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.models.dto.ClientDto;

public class ClientMapper {

    public static ClientDto toClientDto(Client client) {
        return ClientDto.builder()
                .idClient(client.getIdClient())
                .email(client.getEmail())
                .name(client.getName())
                .build();
    }

    public static Client toClient(ClientDto dto) {
        return Client.builder()
                .idClient(dto.getIdClient())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }
}
