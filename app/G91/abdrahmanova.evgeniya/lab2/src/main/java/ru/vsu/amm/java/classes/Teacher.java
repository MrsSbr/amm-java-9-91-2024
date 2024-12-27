package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstracts.Person;
import ru.vsu.amm.java.enums.Type;
import ru.vsu.amm.java.enums.Subjects;

import java.util.Objects;

public class Teacher extends Person {
    private Subjects subject;//todo enum

    public Teacher(String surname, String name, Subjects subject) {
        super(surname, name, Type.Worker);
        this.subject = subject;
    }

    public Subjects getSubject() { return subject; }

    @Override
    public void duty() { System.out.println(getSurname() + " " + getName() + "is instruct");}

    @Override
    public String toString() { return super.toString() + ". His subject: "+ subject;}

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Teacher teacher = (Teacher) obj;
        return subject == teacher.subject;
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), subject);}
}
