package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.models.dto.SessionDto;

public class SessionMapper {
    public static SessionDto toSessionDto(Session session) {
        return SessionDto.builder()
                .idSession(session.getIdSession())
                .idClient(session.getIdClient())
                .idPsychologist(session.getIdPsychologist())
                .date(session.getDate())
                .price(session.getPrice())
                .duration(session.getDuration())
                .build();
    }

    public static Session toSession(SessionDto sessionDto) {
        return Session.builder()
                .idSession(sessionDto.getIdSession())
                .idClient(sessionDto.getIdClient())
                .idPsychologist(sessionDto.getIdPsychologist())
                .date(sessionDto.getDate())
                .price(sessionDto.getPrice())
                .duration(sessionDto.getDuration())
                .build();
    }
}
