package ru.vsu.amm.java;

import java.util.Objects;

public class AmmoWarehouse extends WarehouseImpl {

    private AmmoType storedAmmoType;

    public AmmoWarehouse() {
        super();
    }

    public AmmoWarehouse(String name,
                         String address,
                         int count,
                         AmmoType storedAmmoType) {
        super(name, address, count);
        this.storedAmmoType = storedAmmoType;
    }

    @Override
    public void account() {
        System.out.println(name + ": " + count + " of " + storedAmmoType + "ammo");
    }

    public void add(int capacity) {
        count += capacity;
    }

    @Override
    public void empty() {
        System.out.println("Выписываем патроны, сразу по 50000");
        while (count / 50000 > 0) {
            System.out.println("-50000");
            count -= 50000;
        }
        System.out.println("Недостаточно патронов на складе");
        super.empty();
    }

    @Override
    public String toString() {
        return "Склад патронов\n" +
                super.toString() +
                "\nТип патронов: " + storedAmmoType;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((AmmoWarehouse) obj).storedAmmoType == storedAmmoType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, storedAmmoType);
    }
}
