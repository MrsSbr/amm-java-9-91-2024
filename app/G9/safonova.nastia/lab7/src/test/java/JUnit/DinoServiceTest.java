package JUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Entities.Dino;
import ru.vsu.amm.java.Enums.Kind;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.DinoRepository;
import ru.vsu.amm.java.Services.DinoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class DinoServiceTest {
    private DinoService dinoService;
    private DinoRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(DinoRepository.class);
        dinoService = new DinoService();
        dinoService.dinoRepository = repository;
    }

    @Test
    void createDinoSuccess() throws SQLException {
        Dino dino = new Dino(1L, 500.0, LocalDate.now(), LocalDate.now().plusYears(1), Kind.DIPLODOK, "Dino1");
        assertDoesNotThrow(() -> dinoService.createDino(dino));
        verify(repository).save(dino);
    }

    @Test
    void createDinoInvalidKind() {
        Dino dino = new Dino();
        dino.setKindOfDino(null);
        assertThrows(NullPointerException.class, () -> dinoService.createDino(dino));
    }

    @Test
    void getAllDinosSuccess() throws SQLException {
        when(repository.findAll()).thenReturn(List.of(new Dino(), new Dino()));
        assertEquals(2, dinoService.getAllDinos().size());
    }

    @Test
    void getAllDinosDatabaseError() throws SQLException {
        when(repository.findAll()).thenThrow(new SQLException());
        assertThrows(DbException.class, () -> dinoService.getAllDinos());
    }

    @Test
    void getDinoByIdSuccess() throws SQLException {
        Dino dino = new Dino(1L, 500.0, LocalDate.now(), null, Kind.TRICERATOPS, "Dino2");
        when(repository.findById(1L)).thenReturn(Optional.of(dino));
        assertEquals(dino, dinoService.getDinoById(1L));
    }

    @Test
    void getDinoByIdNotFound() throws SQLException {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> dinoService.getDinoById(999L));
    }

    @Test
    void updateDinoSuccess() throws SQLException {
        Dino existing = new Dino(1L, 400.0, LocalDate.now(), null, Kind.PTERODACTYL, "Old");
        Dino updated = new Dino(1L, 450.0, LocalDate.now(), null, Kind.PTERODACTYL, "New");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        assertDoesNotThrow(() -> dinoService.updateDino(1L, updated));
        verify(repository).update(updated);
    }

    @Test
    void deleteDinoSuccess() throws SQLException {
        Dino dino = new Dino(1L, 500.0, LocalDate.now(), null, Kind.DIPLODOK, "Dino3");
        when(repository.findById(1L)).thenReturn(Optional.of(dino));
        assertDoesNotThrow(() -> dinoService.deleteDino(1L));
        verify(repository).delete(dino);
    }

    @Test
    void deleteDinoDatabaseError() throws SQLException {
        when(repository.findById(anyLong())).thenThrow(new SQLException());
        assertThrows(DbException.class, () -> dinoService.deleteDino(1L));
    }
}