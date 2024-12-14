package ru.vsu.amm.java.classes.entity;

import ru.vsu.amm.java.classes.enums.Department;

public class Employee {
    private final Department idDepartment;
    private final String name;
    private final double salary;

    public Employee(Department idDepartment, String name, double salary) {
        this.idDepartment = idDepartment;
        this.name = name;
        this.salary = salary;
    }

    public Department getIdDepartment() {
        return idDepartment;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Employee [idDepartment = %d; name = %d; salary = %d]", idDepartment, name, salary);
    }
}
