package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.BombType;

import java.util.Objects;

public class BombWarehouse extends WarehouseImpl {

    private BombType storedBombType;

    public BombWarehouse() {
        super();
    }

    public BombWarehouse(String name,
                         String address,
                         int count,
                         BombType storedBombType) {
        super(name, address, count);
        this.storedBombType = storedBombType;
    }

    @Override
    public void account() {
        System.out.println(name + ": " + count + " bombs with a total of " +
                storedBombType.tntEquivalent * count + " kg of TNT");
    }

    public void add(int capacity) {
        count += capacity;
    }

    @Override
    public void empty() {
        System.out.println("Выписываем бомбы по одной");
        while (count > 0) {
            System.out.println("-" + storedBombType);
            count--;
        }
        System.out.println("Бомб на складе не осталось");
        super.empty();
    }

    @Override
    public String toString() {
        return "Склад бомб\n" +
                super.toString() +
                "\nТип бомб: " + storedBombType;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((BombWarehouse) obj).storedBombType == storedBombType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, storedBombType);
    }
}
