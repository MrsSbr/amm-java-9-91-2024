package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.interfaces.Warehouse;

import java.util.Objects;

public abstract class WarehouseImpl implements Warehouse {

    protected String name;

    protected String address;

    protected int count;

    public WarehouseImpl() {
    }

    public WarehouseImpl(String name,
                         String address,
                         int count) {
        this.name = name;
        this.address = address;
        this.count = count;
    }

    public void empty() {
        System.out.println("Плаки-плаки");
    }

    @Override
    public String toString() {
        return  "Название: " +  name +
                "\nАдрес: " + address +
                "\nКоличество: " + count;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        WarehouseImpl other = (WarehouseImpl) obj;
        return name.equals(other.name) && address.equals(other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
