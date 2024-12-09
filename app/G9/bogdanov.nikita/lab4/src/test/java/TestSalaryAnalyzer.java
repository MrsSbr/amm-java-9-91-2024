import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.service.SalaryAnalyzer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSalaryAnalyzer {
    @Test
    public void testMaxAverageSalary() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(1, "Sergey", 50000),
                new Employee(2, "Oleg", 100000),
                new Employee(1, "Vika", 75000),
                new Employee(2, "Kirill", 80000)
        );

        assertEquals(2, sa.findMaxAverageSalary(employees));
    }

    @Test
    public void testMaxSalary() {
        SalaryAnalyzer sa = new SalaryAnalyzer();
        List<Employee> employees = List.of(
                new Employee(1, "Sergey", 70000),
                new Employee(2, "Oleg", 10000),
                new Employee(1, "Vika", 110000),
                new Employee(2, "Kirill", 80000),
                new Employee(1, "Ira", 75000),
                new Employee(1, "Sveta", 50000)
        );

        assertEquals(1, sa.findMaxSalary(employees));
    }
}
