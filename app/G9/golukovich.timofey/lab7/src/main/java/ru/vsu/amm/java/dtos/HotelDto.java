package ru.vsu.amm.java.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HotelDto {
        private Integer id;
        private String name;
        private String address;
        private String email;
        private String phoneNumber;
        private LocalDate openingDate;

    public HotelDto(int id, String name, String address, String email,
                       String phoneNumber, LocalDate openingDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.openingDate = openingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public String getFormattedOpeningDate() {
        return openingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
