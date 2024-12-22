package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Item {
    private String name;
    private int price;
    private int quantity;

    public Item(String name,
                int price,
                int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public Item() {
    }

    abstract void perform();

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
        return "Item:\n" +
                "Name=" + name + '\n' +
                "Price=" + price + '\n' +
                "Count=" + quantity + '\n';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Item that = (Item) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}