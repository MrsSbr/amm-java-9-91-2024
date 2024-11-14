package ru.vsu.amm.java.records;

import ru.vsu.amm.java.enums.DrinkName;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ListDrinks {
    public static Set<String> LIST_DRINKS = new HashSet<>(
            EnumSet.allOf(DrinkName.class)
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.toSet())
    );
}
