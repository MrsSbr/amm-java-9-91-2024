package ru.vsu.amm.java.Illness;

import ru.vsu.amm.java.Person;

import java.util.List;
import java.util.Objects;

public abstract class Illness {

    protected String name;
    protected int usualDamage;
    protected List<String> symptoms;

    public Illness(String name, int usualDamage, List<String> syptoms) {
        this.name = name;
        this.usualDamage = usualDamage;
        this.symptoms = syptoms;
    }

    abstract public void infect(Person p);

    public List<String> getSyptoms() {
        return symptoms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Illness illness = (Illness) o;
        return usualDamage == illness.usualDamage && Objects.equals(symptoms, illness.symptoms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usualDamage, symptoms);
    }

    @Override
    public String toString() {
        return "Illness{" +
                "name='" + name + '\'' +
                "usualDamage=" + usualDamage +
                ", symptoms=" + symptoms +
                '}';
    }
}
