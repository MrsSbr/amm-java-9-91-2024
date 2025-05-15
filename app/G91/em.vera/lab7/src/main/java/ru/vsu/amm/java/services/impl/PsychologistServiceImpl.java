package ru.vsu.amm.java.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.mappers.PsychologistMapper;
import ru.vsu.amm.java.models.dto.PsychologistDto;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;
import ru.vsu.amm.java.repository.impl.PsychologistRepository;
import ru.vsu.amm.java.services.PsychologistService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PsychologistServiceImpl implements PsychologistService {
    private final PsychologistRepository psychologistRepository;

    public PsychologistServiceImpl(){
        psychologistRepository = new PsychologistRepository();
    }

    @Override
    public void login(PsychologistLoginRequest request) {
        try {
            Optional<Psychologist> psychOpt = psychologistRepository.findByLogin(request.login());
            if (psychOpt.isEmpty()) {
                throw new WrongUserCredentialsException(
                        "Psychologist with login " + request.login() + " not found"
                );
            }
            Psychologist psych = psychOpt.get();
            if (!BCrypt.checkpw(request.password(), psych.getPassword())) {
                throw new WrongUserCredentialsException("Incorrect password");
            }
        } catch (SQLException e) {
            log.error("Error logging in psychologist with login={}", request.login(), e);
            throw new DataAccessException("Database error", e);
        }
    }

    @Override
    public void register(PsychologistRegisterRequest request) {
        try {
            Optional<Psychologist> psychOpt = psychologistRepository.findByLogin(request.login());
            if (psychOpt.isPresent()) {
                throw new WrongUserCredentialsException(
                        "Psychologist with login " + request.login() + " already exists"
                );
            }

            Psychologist newPsych = Psychologist.builder()
                    .name(request.name())
                    .surname(request.surname())
                    .birthdate(request.birthdate())
                    .gender(request.gender())
                    .experience(request.experience())
                    .login(request.login())
                    .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                    .build();

            psychologistRepository.save(newPsych);
        } catch (SQLException e) {
            log.error("Error registering psychologist with login={}", request.login(), e);
            throw new DataAccessException("Database error", e);
        }
    }

    @Override
    public List<PsychologistDto> getAllPsychologist() {
        try {
            return psychologistRepository.findAll().stream()
                    .map(PsychologistMapper::toPsychologistDto)
                    .toList();
        } catch (SQLException e) {
            log.error("Error fetching all psychologists", e);
            throw new DataAccessException("Failed to fetch psychologists", e);
        }
    }

    @Override
    public Optional<PsychologistDto> getPsychologistByLogin(String login){
        try {
            return psychologistRepository.findByLogin(login).map(PsychologistMapper::toPsychologistDto);
        } catch (SQLException e) {
            log.error("Error fetching psychologist by login", e);
            throw new DataAccessException("Failed to fetch psychologist by login", e);
        }
    }

    @Override
    public Optional<PsychologistDto> getPsychologistById(Long id){
        try {
            return psychologistRepository.findById(id).map(PsychologistMapper::toPsychologistDto);
        } catch (SQLException e) {
            log.error("Error fetching all psychologist by id", e);
            throw new DataAccessException("Failed to fetch psychologist by id", e);
        }
    }
}
