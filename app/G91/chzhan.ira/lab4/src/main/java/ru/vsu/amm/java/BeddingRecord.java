package ru.vsu.amm.java;

import ru.vsu.amm.java.enums.*;
import java.time.LocalDate;
public class BeddingRecord {
    public LocalDate date;
    public String name;
    public Size size;
    public Colors color;
    public Material material;

    public BeddingRecord(LocalDate date, String name, Size size, Colors color, Material material) {
        this.date = date;
        this.name = name;
        this.size = size;
        this.color = color;
        this.material = material;
    }

    public LocalDate getDate() {return date; }
    public String getName() {return name; }
    public Size getSize() { return size; }
    public Material getMaterial() { return material; }
    public Colors getColor() { return color; }

    @Override
    public String toString() {
        return date + ";" + name + ";" + size + ";" + material + ";" + color;
    }
}
