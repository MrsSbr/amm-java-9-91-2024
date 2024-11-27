package ru.vsu.amm.java;

import java.util.Objects;

public class SystemAdministrator extends  Person {
    private int building;

    public SystemAdministrator(String surname, String name, int building) {
        super(surname, name, Type.Worker);
        this.building = building;
    }

    public int getBuilding() { return building; }

    @Override
    public void duty() { System.out.println(getSurname() + " " + getName() + " is working");}

    @Override
    public String toString() { return super.toString() + ". Building, where he's working: "+ building;}

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        SystemAdministrator admin = (SystemAdministrator) obj;
        return building == admin.building;
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), building);}
}
