package ru.vsu.amm.java.classes.service;

import ru.vsu.amm.java.classes.entity.Employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalaryAnalyzer {
    public void printByDepartment(List<Employee> employees) {
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getIdDepartment))
                .forEach((idDepartment, empList) -> {
                    System.out.printf("Department: %s\n", idDepartment);
                    empList.forEach(emp -> System.out.printf("\tEmployee: %s\n", emp));
                });
    }

    public int findMaxAverageSalary(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getIdDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No department!"))
                .getKey();
    }

    public int findMaxSalary(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getIdDepartment, Collectors.summingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("No department!"))
                .getKey();
    }
}
