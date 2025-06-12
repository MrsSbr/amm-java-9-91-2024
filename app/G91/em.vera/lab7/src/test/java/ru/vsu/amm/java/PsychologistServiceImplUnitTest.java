package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;
import ru.vsu.amm.java.repository.impl.PsychologistRepository;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;
import ru.vsu.amm.java.utils.TestDataConstants;
import ru.vsu.amm.java.utils.TestDataFactory;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PsychologistServiceImplUnitTest {

    private PsychologistServiceImpl service;
    private PsychologistRepository repo;

    @BeforeEach
    void setUp() {
        repo = mock(PsychologistRepository.class);
        service = new PsychologistServiceImpl(repo);
    }

    @Test
    void loginShouldPassWhenCorrect() throws SQLException {
        Psychologist p = TestDataFactory.psychologistEntity();
        when(repo.findByLogin(p.getLogin())).thenReturn(Optional.of(p));

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.checkpw(TestDataConstants.PSYCHOLOGIST_PASSWORD, p.getPassword())).thenReturn(true);
            assertDoesNotThrow(() ->
                    service.login(new PsychologistLoginRequest(p.getLogin(), TestDataConstants.PSYCHOLOGIST_PASSWORD))
            );
        }
        verify(repo).findByLogin(p.getLogin());
    }

    @Test
    void loginShouldThrowWhenNotFound() throws SQLException {
        when(repo.findByLogin("none")).thenReturn(Optional.empty());
        assertThrows(WrongUserCredentialsException.class,
                () -> service.login(new PsychologistLoginRequest("none", TestDataConstants.PSYCHOLOGIST_PASSWORD))
        );
        verify(repo).findByLogin("none");
    }

    @Test
    void loginShouldThrowWhenBadPassword() throws SQLException {
        Psychologist p = TestDataFactory.psychologistEntity();
        when(repo.findByLogin(p.getLogin())).thenReturn(Optional.of(p));
        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.checkpw("bad", p.getPassword())).thenReturn(false);
            assertThrows(WrongUserCredentialsException.class,
                    () -> service.login(new PsychologistLoginRequest(p.getLogin(), "bad"))
            );
        }
        verify(repo).findByLogin(p.getLogin());
    }

    @Test
    void registerShouldThrowWhenExists() throws SQLException {
        PsychologistRegisterRequest req = TestDataFactory.validPsychologistRegisterRequest();
        when(repo.findByLogin(req.login())).thenReturn(Optional.of(TestDataFactory.psychologistEntity()));

        assertThrows(WrongUserCredentialsException.class,
                () -> service.register(req)
        );
        verify(repo).findByLogin(req.login());
    }

    @Test
    void registerShouldSaveWhenNew() throws SQLException {
        PsychologistRegisterRequest req = TestDataFactory.validPsychologistRegisterRequest();
        when(repo.findByLogin(req.login())).thenReturn(Optional.empty());

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.gensalt()).thenReturn("salt");
            bc.when(() -> BCrypt.hashpw(TestDataConstants.PSYCHOLOGIST_PASSWORD, "salt")).thenReturn("h");

            service.register(req);

            ArgumentCaptor<Psychologist> captor = ArgumentCaptor.forClass(Psychologist.class);
            verify(repo).save(captor.capture());

            Psychologist saved = captor.getValue();

            assertEquals(TestDataConstants.PSYCHOLOGIST_NAME, saved.getName());
            assertEquals(TestDataConstants.PSYCHOLOGIST_SURNAME, saved.getSurname());
            assertEquals(TestDataConstants.PSYCHOLOGIST_BIRTHDATE, saved.getBirthdate());
            assertEquals(TestDataConstants.PSYCHOLOGIST_EXPERIENCE, saved.getExperience());
            assertEquals(TestDataConstants.PSYCHOLOGIST_GENDER, saved.getGender());
            assertEquals(TestDataConstants.PSYCHOLOGIST_LOGIN, saved.getLogin());
            assertEquals("h", saved.getPassword());
        }
    }
}
