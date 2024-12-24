package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Gemstone;
import ru.vsu.amm.java.enums.PreciousMetal;
import ru.vsu.amm.java.enums.Jewelry;

import java.time.LocalDate;
import java.util.Set;


public final class SaleRecord {
    private LocalDate date;
    private PreciousMetal preciousMetal;
    private Jewelry jewelry;
    private Set<Gemstone> gemstones;
    private int price;

    public SaleRecord(LocalDate date, PreciousMetal preciousMetal,
                      Jewelry jewelry, Set<Gemstone> gemstones, int price) {
        this.date = date;
        this.preciousMetal = preciousMetal;
        this.jewelry = jewelry;
        this.gemstones = gemstones;
        this.price = price;
    }

    public void setDate(LocalDate date) { this.date = date; }
    public void setPreciousMetal(PreciousMetal preciousMetal) { this.preciousMetal = preciousMetal; }
    public void setJewelry(Jewelry jewelry) { this.jewelry = jewelry; }
    public void setGemstones(Set<Gemstone> gemstones) { this.gemstones = gemstones; }
    public void setPrice(int price) { this.price = price; }

    public LocalDate getDate() { return date; }
    public PreciousMetal getPreciousMetal() { return preciousMetal; }
    public Jewelry getJewelry() { return jewelry; }
    public Set<Gemstone> getGemstones() { return gemstones; }
    public int getPrice() { return price; }

    @Override
    public String toString() { return "Date: " + date
                                        + "; metal: " + preciousMetal
                                        + "; jewelry type: " + jewelry
                                        + "; gemstones: " + gemstones
                                        + "; price: " + price;
    }

}