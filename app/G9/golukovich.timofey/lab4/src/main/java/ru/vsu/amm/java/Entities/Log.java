package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.HttpResponseCode;
import ru.vsu.amm.java.Enums.Resource;

import java.time.LocalDate;

public record Log(LocalDate date, Resource resource, int ip, HttpResponseCode httpResponseCode) {
}
