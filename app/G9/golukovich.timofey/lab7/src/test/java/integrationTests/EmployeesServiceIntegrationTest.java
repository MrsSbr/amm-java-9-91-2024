package integrationTests;

import dbConfigurator.DatabaseHelper;
import factories.EmployeesFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.repository.EmployeeRepo;
import ru.vsu.amm.java.services.EmployeesService;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeesServiceIntegrationTest {
    private static EmployeesService employeesService;
    private static EmployeeRepo employeeRepo;

    @BeforeAll
    public static void createTables() throws SQLException, IOException {
        DatabaseHelper.createTables();
        DatabaseHelper.truncateTables();

        employeeRepo = new EmployeeRepo();
        employeesService = new EmployeesServiceImpl();
    }

    @AfterAll
    public static void dropTables() throws SQLException, IOException {
        DatabaseHelper.dropTables();
    }

    @AfterEach
    public void truncateTables() throws SQLException, IOException {
        DatabaseHelper.truncateTables();
    }

    @Test
    @DisplayName("Успешная попытка поиска существующего сотрудника")
    public void testCreateNewEmployee() throws SQLException {
        var builder = new EmployeesFactory();
        var login = "employee_login";
        var entity = builder.setDefaultEmployeeEntity().buildEntity();
        entity.setLogin(login);
        entity = employeeRepo.save(entity);

        var dto = assertDoesNotThrow(() -> employeesService.getEmployeeByLogin(login, false));

        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    @DisplayName("Исключение при попытке поиска несуществующего сотрудника")
    public void testGetEmployeeByLoginThrowsWhenDoesNotExists() throws SQLException {
        var login = "employee_login";
        assertThrows(EmployeeNotFoundException.class, () -> employeesService.getEmployeeByLogin(login, false));
    }

    @Test
    @DisplayName("Успешное получение всех сотрудников")
    public void testDeleteEmployee() throws SQLException {
        var dtos = employeesService.getAllEmployees(false);
        assertEquals(0, dtos.size());

        var builder = new EmployeesFactory();
        var entity = builder.setDefaultEmployeeEntity().buildEntity();
        employeeRepo.save(entity);

        dtos = employeesService.getAllEmployees(false);
        assertEquals(1, dtos.size());

        entity = builder.setDefaultEmployeeEntity().next().buildEntity();
        employeeRepo.save(entity);

        dtos = employeesService.getAllEmployees(false);
        assertEquals(2, dtos.size());
    }
}
