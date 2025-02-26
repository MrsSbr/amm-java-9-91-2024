package ru.vsu.amm.java.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.vsu.amm.java.enums.HttpResponseCode;
import ru.vsu.amm.java.enums.Resource;

import java.time.LocalDate;

public record Log(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        Resource resource,
        int ip,
        HttpResponseCode httpResponseCode) {
}
