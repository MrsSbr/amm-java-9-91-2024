package ru.vsu.amm.java.Entities;

public class User {
    private int id;
    private String fullName;
    private int Age;
    private String eMail;
    private String numberPhone;

    public User(int id, String fullName, int Age, String eMail, String numberPhone) {
        this.id = id;
        this.fullName = fullName;
        this.Age = Age;
        this.eMail = eMail;
        this.numberPhone = numberPhone;
    }

    public int getId() { return id; }
    public void setId(int id) {this.id = id;}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return Age; }
    public void setAge(int age) { Age = age; }

    public String geteMail() { return eMail; }
    public void seteMail(String eMail) { this.eMail = eMail; }

    public String getNumberPhone() { return numberPhone; }
    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }
}
