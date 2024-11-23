package ru.vsu.amm.java;

import java.util.Objects;

public abstract class ItemImplementation implements Item {
    private String name;
    private int price;
    private int quantity;

    public ItemImplementation(String name,
                              int price,
                              int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public ItemImplementation() {}

    public String getName() {
        return name;
    }

    public void buy(int quantity) {
        if (quantity <= this.quantity) {
            this.quantity -= quantity;
        }
    }


    @Override
    public String toString() {
        return "Товар:" +
                "Название='" + name + '\'' +
                ", Цена=" + price +
                ", Количество=" + quantity;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ItemImplementation that = (ItemImplementation) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}