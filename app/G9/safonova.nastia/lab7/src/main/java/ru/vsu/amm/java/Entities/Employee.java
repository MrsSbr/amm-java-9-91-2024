package ru.vsu.amm.java.Entities;

import java.time.LocalDate;

public class Employee {
    private long idEmpl;
    private String login;
    private String password;
    private String surnameEmpl;
    private String nameEmpl;
    private String patronumicEmpl;
    private LocalDate dateOfBirthEmpl;

    public Employee(long idEmpl, String login, String password, String surnameEmpl, String nameEmpl, String patronumicEmpl, LocalDate dateOfBirthEmpl) {
        this.idEmpl = idEmpl;
        this.login = login;
        this.password = password;
        this.surnameEmpl = surnameEmpl;
        this.nameEmpl = nameEmpl;
        this.patronumicEmpl = patronumicEmpl;
        this.dateOfBirthEmpl = dateOfBirthEmpl;
    }

    public Employee(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Employee() {

    }

    public Employee(String login, String password, String surnameEmpl, String nameEmpl, String patronumicEmpl, LocalDate dateOfBirthEmpl) {
        this.login = login;
        this.password = password;
        this.surnameEmpl = surnameEmpl;
        this.nameEmpl = nameEmpl;
        this.patronumicEmpl = patronumicEmpl;
        this.dateOfBirthEmpl = dateOfBirthEmpl;
    }

    public long getIdEmpl() {
        return idEmpl;
    }

    public void setIdEmpl(long idEmpl) {
        this.idEmpl = idEmpl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSurnameEmpl() {
        return surnameEmpl;
    }

    public void setSurnameEmpl(String surnameEmpl) {
        this.surnameEmpl = surnameEmpl;
    }

    public String getNameEmpl() {
        return nameEmpl;
    }

    public void setNameEmpl(String nameEmpl) {
        this.nameEmpl = nameEmpl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPatronumicEmpl() {
        return patronumicEmpl;
    }

    public void setPatronumicEmpl(String patronumicEmpl) {
        this.patronumicEmpl = patronumicEmpl;
    }

    public LocalDate getDateOfBirthEmpl() {
        return dateOfBirthEmpl;
    }

    public void setDateOfBirthEmpl(LocalDate dateOfBirthEmpl) {
        this.dateOfBirthEmpl = dateOfBirthEmpl;
    }
}