package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.annotation.SelectTo;
import ru.vsu.amm.java.annotation.Table;

@Table(name = "TeaBag")
public record TeaBag(Integer id, @SelectTo String name, @SelectTo Integer year, Integer weight) {
}


