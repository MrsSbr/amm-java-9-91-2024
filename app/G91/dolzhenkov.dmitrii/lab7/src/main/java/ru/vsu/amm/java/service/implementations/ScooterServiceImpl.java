package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.entities.ScooterEntity;
import ru.vsu.amm.java.enums.ScooterStatus;
import ru.vsu.amm.java.model.requests.ScooterRequest;
import ru.vsu.amm.java.repository.ScooterRepository;
import ru.vsu.amm.java.service.interfaces.ScooterService;

public class ScooterServiceImpl implements ScooterService {
    private final ScooterRepository scooterRepository;

    public ScooterServiceImpl() {
        this.scooterRepository = new ScooterRepository();
    }

    @Override
    public void addScooter(ScooterRequest request) {
        scooterRepository.save(new ScooterEntity(null, request.model(), request.latitude(),
                request.longitude(), ScooterStatus.FREE));
    }
}
