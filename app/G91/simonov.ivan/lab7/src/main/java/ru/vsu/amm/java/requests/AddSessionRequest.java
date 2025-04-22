package ru.vsu.amm.java.requests;

import java.math.BigDecimal;

public record AddSessionRequest(int userId,
                                BigDecimal parkingPrice,
                                AddVehicleRequest addVehicleRequest) {
}
