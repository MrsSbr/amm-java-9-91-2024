package ru.vsu.amm.java.enums;

import lombok.Getter;

@Getter
public enum BookType {
    ARTICLE ("Статья"),
    ESSAY   ("Эссе"),
    TEXTBOOK("Учебник"),
    FICTION ("Художественная литература");

    private final String ruName;

    BookType(String ruName) {
        this.ruName = ruName;
    }
}