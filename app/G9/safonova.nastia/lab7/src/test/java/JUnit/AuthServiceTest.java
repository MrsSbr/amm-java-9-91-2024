package JUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.vsu.amm.java.Entities.Employee;
import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.EmployeeRepository;
import ru.vsu.amm.java.Services.AuthService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class AuthServiceTest {
    private AuthService authService;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(EmployeeRepository.class);
        authService = new AuthService();
        authService.employeeRepository = repository;
    }

    @Test
    void loginSuccess() throws SQLException {
        Employee employee = new Employee("test", "pass");
        when(repository.findByLogin("test")).thenReturn(Optional.of(employee));

        assertDoesNotThrow(() -> authService.login("test", "pass"));
        verify(repository).findByLogin("test");
    }

    @Test
    void loginUserNotFound() throws SQLException {
        when(repository.findByLogin(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authService.login("test", "pass"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void loginInvalidPassword() throws SQLException {
        Employee employee = new Employee("test", "pass");
        when(repository.findByLogin("test")).thenReturn(Optional.of(employee));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authService.login("test", "wrong"));
        assertEquals("Invalid Password", exception.getMessage());
    }

    @Test
    void loginDatabaseError() throws SQLException {
        when(repository.findByLogin(anyString())).thenThrow(new SQLException());

        assertThrows(DbException.class, () -> authService.login("test", "pass"));
    }

    @Test
    void registerSuccess() throws SQLException {
        Employee employee = new Employee(1L, "new", "pass", "Surname", "Name", "Patr", LocalDate.of(1990, 1, 1));
        when(repository.findByLogin("new")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> authService.register(employee));
        verify(repository).save(employee);
    }

    @Test
    void registerAlreadyExists() throws SQLException {
        Employee existingEmployee = new Employee(1L, "exist", "pass", "Surname", "Name", "Patr", LocalDate.of(1990, 1, 1));
        when(repository.findByLogin("exist")).thenReturn(Optional.of(existingEmployee));

        Employee newEmployee = new Employee(2L, "exist", "newPass", "Surname", "Name", "Patr", LocalDate.of(1990, 1, 1));
        AlreadyExistException exception = assertThrows(AlreadyExistException.class,
                () -> authService.register(newEmployee));
        assertEquals("User with this login already exists", exception.getMessage());
    }

    @Test
    void registerUnderage() {
        Employee employee = new Employee(1L, "young", "pass", "S", "N", "P", LocalDate.now().minusYears(17));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authService.register(employee));
        assertEquals("Employee must be older than 18 years", exception.getMessage());
    }

    @Test
    void registerInvalidCredentials() {
        Employee emptyLogin = new Employee("", "pass");
        Employee emptyPass = new Employee("login", "");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> authService.register(emptyLogin)),
                () -> assertThrows(IllegalArgumentException.class, () -> authService.register(emptyPass))
        );
    }

    @Test
    void registerDatabaseError() throws SQLException {
        Employee employee = new Employee(1L, "dberror", "pass", "Surname", "Name", "Patr", LocalDate.of(1990, 1, 1));
        when(repository.findByLogin("dberror")).thenThrow(new SQLException());

        assertThrows(DbException.class, () -> authService.register(employee));
    }
}