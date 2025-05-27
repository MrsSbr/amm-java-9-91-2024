package Integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Entities.Employee;
import ru.vsu.amm.java.Repository.EmployeeRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository;
    private Employee testEmployee;

    @BeforeEach
    void setup() throws SQLException {
        employeeRepository = new EmployeeRepository();

        testEmployee = new Employee(
                "test_login",
                "test_pass",
                "Ivanov",
                "Ivan",
                "Ivanovich",
                LocalDate.of(1990, 1, 1)
        );

        employeeRepository.save(testEmployee);

        if (testEmployee.getIdEmpl() == 0) {
            throw new SQLException("Failed to generate employee ID");
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
        if (testEmployee.getIdEmpl() != 0) {
            Employee employeeToDelete = new Employee();
            employeeToDelete.setIdEmpl(testEmployee.getIdEmpl());
            employeeRepository.delete(employeeToDelete);
        }
    }

    @Test
    void testSaveAndFindById() throws SQLException {
        Optional<Employee> found = employeeRepository.findById(testEmployee.getIdEmpl());
        assertTrue(found.isPresent());
        assertEquals("test_login", found.get().getLogin());
        assertEquals("Ivanov", found.get().getSurnameEmpl());
    }

    @Test
    void testFindByLogin() throws SQLException {
        Optional<Employee> found = employeeRepository.findByLogin("test_login");
        assertEquals(testEmployee.getLogin(), found.get().getLogin());
    }

    @Test
    void testUpdate() throws SQLException {
        testEmployee.setSurnameEmpl("Petrov");
        employeeRepository.update(testEmployee);

        Employee updated = employeeRepository.findById(testEmployee.getIdEmpl()).get();
        assertEquals("Petrov", updated.getSurnameEmpl());
    }

    @Test
    void testDelete() throws SQLException {
        employeeRepository.delete(testEmployee);
        Optional<Employee> deleted = employeeRepository.findById(testEmployee.getIdEmpl());
        assertFalse(deleted.isPresent());
    }
}