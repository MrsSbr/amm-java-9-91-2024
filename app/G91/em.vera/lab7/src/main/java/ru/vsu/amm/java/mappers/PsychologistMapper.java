package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.models.dto.PsychologistDto;

public class PsychologistMapper {
    public static PsychologistDto toPsychologistDto(Psychologist psychologist) {
        return PsychologistDto.builder()
                .idPsychologist(psychologist.getIdPsychologist())
                .name(psychologist.getName())
                .surname(psychologist.getSurname())
                .login(psychologist.getLogin())
                .birthdate(psychologist.getBirthdate())
                .gender(psychologist.getGender())
                .experience(psychologist.getExperience())
                .build();
    }

    public static Psychologist toPsychologist(PsychologistDto dto) {
        return Psychologist.builder()
                .idPsychologist(dto.getIdPsychologist())
                .name(dto.getName())
                .surname(dto.getSurname())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .experience(dto.getExperience())
                .login(dto.getLogin())
                .build();
    }
}
