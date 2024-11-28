package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.table.Column;
import ru.vsu.amm.java.table.Table;

@Table(name = "MyTable")
public class MyEntity {
    @Column(name = "id", type = "INT")
    private int id;

    @Column(name = "name", type = "VARCHAR")
    private String name;

    @Column(name = "age", type = "INT")
    private int age;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
