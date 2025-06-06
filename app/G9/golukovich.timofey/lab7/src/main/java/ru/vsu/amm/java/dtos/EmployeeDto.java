package ru.vsu.amm.java.dtos;

import ru.vsu.amm.java.enums.EmployeePost;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeDto {
    private Integer id;
    private Integer hotelId;
    private String login;
    private String name;
    private String phoneNumber;
    private String email;
    private String passportNumber;
    private String passportSeries;
    private EmployeePost post;
    private LocalDate birthday;

    public EmployeeDto(Integer id, Integer hotelId, String login, String name,
                       String phoneNumber, String email, String passportNumber, String passportSeries,
                       EmployeePost post, LocalDate birthday) {
        this.id = id;
        this.hotelId = hotelId;
        this.login = login;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
        this.passportSeries = passportSeries;
        this.post = post;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public EmployeePost getPost() {
        return post;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getFormattedBirthday() {
        if (birthday != null) {
            return birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } else {
            return "";
        }
    }
}
