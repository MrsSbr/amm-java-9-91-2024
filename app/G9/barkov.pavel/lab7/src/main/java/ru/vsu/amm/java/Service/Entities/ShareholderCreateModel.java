package ru.vsu.amm.java.Service.Entities;

import ru.vsu.amm.java.Repository.Entities.Shareholder;

public record ShareholderCreateModel(
        String fio,
        String document,
        String email,
        String phone
) {
}
