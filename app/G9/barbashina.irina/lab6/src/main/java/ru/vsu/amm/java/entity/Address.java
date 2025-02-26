package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.annotation.Column;
import ru.vsu.amm.java.annotation.Id;
import ru.vsu.amm.java.annotation.Table;

@Table(name = "addresses")
public class Address {
    @Id
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
