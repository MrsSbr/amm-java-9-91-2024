package ru.vsu.amm.java.classes.entity;


public class Employee {
    private final int idDepartment;
    private final String name;
    private final double salary;

    public Employee(int idDepartment, String name, double salary) {
        this.idDepartment = idDepartment;
        this.name = name;
        this.salary = salary;
    }

    public int getIdDepartment() {
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
