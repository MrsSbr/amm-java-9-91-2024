package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class User {
    private final static String NAME_REGEX = "^[A-Z][a-z'.]+$";
    @Setter
    private Integer userId;
    private String email;
    @Setter
    private String password;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;

    public User() {
    }

    public void setEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public void setLastName(String lastName) {
        if (!lastName.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("Invalid last name, latin letters only");
        }
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        if (!firstName.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("Invalid first name, latin letters only");
        }
        this.firstName = firstName;
    }

    public void setPatronymic(String patronymic) {
        if (!patronymic.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("Invalid patronymic name, latin letters only");
        }
        this.patronymic = patronymic;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^7[0-9]{10}$")) {
            throw new IllegalArgumentException("Invalid phone number, russian format only");
        }
        this.phoneNumber = phoneNumber;
    }
}
