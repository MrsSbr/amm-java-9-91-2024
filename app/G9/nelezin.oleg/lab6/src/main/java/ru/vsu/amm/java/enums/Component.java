package ru.vsu.amm.java.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Component {

    PAPER("Бумага"),
    MATCHES("Спички"),
    TOBACCO("Табак");

    private final String name;
}
