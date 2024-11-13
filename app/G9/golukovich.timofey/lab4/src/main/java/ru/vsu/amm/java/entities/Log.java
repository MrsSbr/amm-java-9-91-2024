package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.HttpResponseCode;
import ru.vsu.amm.java.enums.Resource;

import java.io.Serializable;
import java.time.LocalDate;

public record Log(LocalDate date, Resource resource,
                  int ip, HttpResponseCode httpResponseCode) implements Serializable {
}
