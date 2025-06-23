package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.Smartphone;
import ru.vsu.amm.java.Repositories.SmartphoneRepository;

import java.util.List;

public class SmartphoneService {
    private final SmartphoneRepository repository;

    public SmartphoneService() {
        this.repository = new SmartphoneRepository();
    }

    public List<Smartphone> getAll() {
        return repository.findAll();
    }
}
