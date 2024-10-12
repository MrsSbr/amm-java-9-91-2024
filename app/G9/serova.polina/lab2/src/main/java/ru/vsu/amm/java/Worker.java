package ru.vsu.amm.java;


import java.util.Objects;

public abstract class Worker implements Workable {

    private final String name;
    private final double salary;

    protected Worker(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public abstract String getPosition();

    @Override
    public boolean equals(Object object) {
        if(object != null) {
            Worker temp = (Worker) object;
            return this.getName().equals(temp.getName())
                    && this.getSalary() == temp.getSalary()
                    && this.getPosition().equals(temp.getPosition());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary, getPosition());
    }

    @Override
    public String toString() {
        return "Worker: " + this.getName() + "\n"
                + "Salary: " + this.getSalary() + "\n"
                + "Position: " + this.getPosition();
    }
}
