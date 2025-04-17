package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.EmployeePost;
import java.time.LocalDate;

public class EmployeeEntity {
    private int id;
    private Integer hotelId;
    private String login;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String passportNumber;
    private String passportSeries;
    private EmployeePost post;
    private Integer salary;
    private LocalDate birthday;

    public EmployeeEntity(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public EmployeeEntity(int id, Integer hotelId, String login, String password, String name,
                          String phoneNumber, String email, String passportNumber, String passportSeries,
                          EmployeePost post, Integer salary, LocalDate birthday) {
        this.id = id;
        this.hotelId = hotelId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
        this.passportSeries = passportSeries;
        this.post = post;
        this.salary = salary;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public EmployeePost getPost() {
        return post;
    }

    public void setPost(EmployeePost post) {
        this.post = post;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
