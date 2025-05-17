package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.dto.ScooterDto;
import ru.vsu.amm.java.model.requests.ScooterRequest;

import java.util.List;

public interface ScooterService {
    void addScooter(ScooterRequest request);
    List<ScooterDto> getAllScooters();
}
