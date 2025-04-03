package ru.vsu.amm.java.Repository.Entities;

public class Shareholder {
    private Integer id;
    private String password;
    private String fio;
    private String document;
    private String email;
    private String phone;

    public Shareholder(Integer id, String password, String fio, String document, String email, String phone) {
        this.id = id;
        this.password = password;
        this.fio = fio;
        this.document = document;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
