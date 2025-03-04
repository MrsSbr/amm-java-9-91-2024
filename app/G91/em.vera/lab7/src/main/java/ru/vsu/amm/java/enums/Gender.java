package ru.vsu.amm.java.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    FEMALE('Ж'),
    MALE('М');

    private final Character character;

    Gender(Character character) {
        this.character = character;
    }

    public static Gender getGenderByChar(Character character) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getCharacter().equals(character))
                .findFirst()
                .orElse(null);
    }
}
