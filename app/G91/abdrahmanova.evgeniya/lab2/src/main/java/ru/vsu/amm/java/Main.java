package ru.vsu.amm.java;

import ru.vsu.amm.java.abstracts.Person;
import ru.vsu.amm.java.classes.Student;
import ru.vsu.amm.java.classes.SystemAdministrator;
import ru.vsu.amm.java.classes.Teacher;
import ru.vsu.amm.java.enums.Subjects;

public class Main {
    // todo разложить по пакетам
    public static void main(String[] args) {
        Person student = new Student("Petrov", "Ivan", 2);
        Person teacher = new Teacher("Ivanov", "Ivan", Subjects.Programming_lecture);
        Person admin = new SystemAdministrator("Sidorov", "Igor", 3);

        //System.out.println();
        universityMemberInfo(student);
        universityMemberInfo(teacher);
        universityMemberInfo(admin);

        areEquals(student, student);
        areEquals(teacher, admin);

        checkInstance(student);
        checkInstance(teacher);
        checkInstance(admin);
    }

    public static void universityMemberInfo(Person person) {
        System.out.println(person);
        person.duty();
    }

    public static void areEquals(Person person1, Person person2) {
        if (person1.equals(person2)) {
            System.out.println("They are equal. ");
        }
        else {
            System.out.println("They aren't equal. ");
        }
    }

    public static void checkInstance(Person person) {
        if (!(person instanceof Person)) {
            System.out.println("Oh, this isn't member of University. ");
        } else {
            if (person instanceof Student) {
                System.out.println("Oh, this is a student. ");
            } else if (person instanceof Teacher) {
                System.out.println("Oh, this is a teacher. ");
            } else if (person instanceof SystemAdministrator) {
                System.out.println("Oh, this is a admin. ");
            }
        }
    }
}