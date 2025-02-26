import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.enums.Department;
import ru.vsu.amm.java.classes.service.SalaryAnalyzer;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSalaryAnalyzer {

    @Test
    public void testMaxAverageSalary() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(Department.HR, "Sergey", 50000),
                new Employee(Department.IT, "Oleg", 100000),
                new Employee(Department.HR, "Vika", 75000),
                new Employee(Department.IT, "Kirill", 80000)
        );

        assertEquals(Department.IT, sa.findMaxAverageSalary(employees));
    }

    @Test
    public void testMaxAverageSalaryFalse() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(Department.HR, "Sergey", 50000),
                new Employee(Department.IT, "Oleg", 1000),
                new Employee(Department.HR, "Vika", 75000),
                new Employee(Department.IT, "Kirill", 80000)
        );

        assertEquals(Department.IT, sa.findMaxAverageSalary(employees));
    }

    @Test
    public void testMaxSalary() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(Department.HR, "Sergey", 70000),
                new Employee(Department.IT, "Oleg", 10000),
                new Employee(Department.HR, "Vika", 110000),
                new Employee(Department.IT, "Kirill", 80000),
                new Employee(Department.HR, "Ira", 75000),
                new Employee(Department.HR, "Sveta", 50000)
        );

        assertEquals(Department.HR, sa.findMaxSalary(employees));
    }

    @Test
    public void testMaxSalaryFalse() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(Department.HR, "Sergey", 70000),
                new Employee(Department.IT, "Oleg", 10000),
                new Employee(Department.HR, "Vika", 110000),
                new Employee(Department.IT, "Kirill", 800000),
                new Employee(Department.HR, "Ira", 75000),
                new Employee(Department.HR, "Sveta", 50000)
        );

        assertEquals(Department.IT, sa.findMaxSalary(employees));
    }

    @Test
    public void testEmptyEmployeeList() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of();

        assertThrows(NoSuchElementException.class, () -> sa.findMaxAverageSalary(employees));
        assertThrows(NoSuchElementException.class, () -> sa.findMaxSalary(employees));
    }

    @Test
    public void testSingleEmployee() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(Department.HR, "Anna", 50000)
        );

        assertEquals(Department.HR, sa.findMaxAverageSalary(employees));
        assertEquals(Department.HR, sa.findMaxSalary(employees));
    }

    @Test
    public void testEqualSalariesAcrossDepartments() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(Department.HR, "Alex", 70000),
                new Employee(Department.IT, "Oleg", 70000)
        );

        assertEquals(Department.IT, sa.findMaxAverageSalary(employees));
        assertEquals(Department.IT, sa.findMaxSalary(employees));
    }
}
