package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.models.dto.SessionDto;

public class SessionMapper {
    public static SessionDto toSessionDto(Session session) {
        return SessionDto.builder()
                .idSession(session.getIdSession())
                .date(session.getDate())
                .price(session.getPrice())
                .duration(session.getDuration())
                .build();
    }
}
