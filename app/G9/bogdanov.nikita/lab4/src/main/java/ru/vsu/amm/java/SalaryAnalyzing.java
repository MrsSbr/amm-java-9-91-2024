package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.utils.ReadingFile;
import ru.vsu.amm.java.classes.service.SalaryAnalyzer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SalaryAnalyzing {
    private static final Logger logger = Logger.getLogger(SalaryAnalyzing.class.getName());

    public static void main(String[] args) {
        try {
            String fileName = "salary.txt";
            ReadingFile reader = new ReadingFile();
            List<Employee> employees = reader.readEmployeeFromFile(fileName);

            SalaryAnalyzer salaryAnalyzer = new SalaryAnalyzer();

            int maxAverageSalary = salaryAnalyzer.findMaxAverageSalary(employees);

            int maxSalary = salaryAnalyzer.findMaxSalary(employees);

            salaryAnalyzer.printByDepartment(employees);

            System.out.println("Department with max average salary : " + maxAverageSalary);
            System.out.println("Department with max sum salary: " + maxSalary);

        } catch (IOException e) {
            logger.severe("Error - " + e.getMessage());
        }
    }
}