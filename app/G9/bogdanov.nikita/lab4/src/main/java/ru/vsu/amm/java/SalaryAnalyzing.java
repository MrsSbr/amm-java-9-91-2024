package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.enums.Department;
import ru.vsu.amm.java.classes.utils.ReadingFile;
import ru.vsu.amm.java.classes.service.SalaryAnalyzer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static ru.vsu.amm.java.classes.service.SalaryAnalyzer.personByDepartment;
import static ru.vsu.amm.java.classes.service.SalaryAnalyzer.printByDepartment;

public class SalaryAnalyzing {

    private static final String FILENAME = "salary.txt";

    private static final Logger logger = Logger.getLogger(SalaryAnalyzing.class.getName());

    public static void main(String[] args) {
        try {
            ;
            ReadingFile reader = new ReadingFile();
            List<Employee> employees = reader.readEmployeeFromFile(FILENAME);

            SalaryAnalyzer salaryAnalyzer = new SalaryAnalyzer();

            Department maxAverageSalary = salaryAnalyzer.findMaxAverageSalary(employees);

            Department maxSalary = salaryAnalyzer.findMaxSalary(employees);

            Map<Department, List<Employee>> map = personByDepartment(employees);

            printByDepartment(map);

            System.out.println("Department with max average salary : " + maxAverageSalary);
            System.out.println("Department with max sum salary: " + maxSalary);

        } catch (IOException e) {
            logger.severe("Error - " + e.getMessage());
        }
    }
}