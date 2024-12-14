package ru.vsu.amm.java.classes.service;

import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.enums.Department;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalaryAnalyzer {

    public static Map<Department, List<Employee>> personByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getIdDepartment));
    }

    public static void printByDepartment(Map<Department, List<Employee>> employees) {
        employees.forEach((department, empList) -> {
            System.out.printf("Department: %s\n", department);
            empList.forEach(emp -> System.out.printf("\tEmployee: %s\n", emp));
        });
    }

    public Department findMaxAverageSalary(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getIdDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No department!"))
                .getKey();
    }

    public Department findMaxSalary(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getIdDepartment, Collectors.summingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No department!"))
                .getKey();
    }
}
