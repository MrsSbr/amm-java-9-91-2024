package ru.vsu.amm.java.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;
import ru.vsu.amm.java.repository.impl.PsychologistRepository;
import ru.vsu.amm.java.services.PsychologistAuthService;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class PsychologistAuthServiceImpl implements PsychologistAuthService {
    private final PsychologistRepository psychologistRepository;

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
}
