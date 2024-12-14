import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.enums.Department;
import ru.vsu.amm.java.classes.service.SalaryAnalyzer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(2, sa.findMaxAverageSalary(employees));
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

        assertEquals(2, sa.findMaxAverageSalary(employees));
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

        assertEquals(1, sa.findMaxSalary(employees));
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

        assertEquals(1, sa.findMaxSalary(employees));
    }
}
