package ru.vsu.amm.java.abstracts;

import ru.vsu.amm.java.interfaces.Universityable;
import ru.vsu.amm.java.enums.Type;

import java.util.Objects;

public abstract class Person implements Universityable {
    private String surname;
    private String name;
    private Type typeMember;

    public Person(String surname, String name, Type typeMember) {
        this.surname = surname;
        this.name = name;
        this.typeMember = typeMember;
    }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public Type getTypeMember() { return typeMember; }

    @Override
    public String toString(){return "Surname: " + surname + ". Name: " + name + ". TypeMember: " + typeMember;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Person)) {
            return false;
        }

        Person person = (Person) obj;
        return surname.equals(person.surname) && name.equals(person.name) && typeMember.equals(person.typeMember);
    }
    @Override
    public int hashCode() {return Objects.hash(surname, name, typeMember);}
}
