package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.RealEstate;
import ru.vsu.amm.java.repository.RealEstateRepository;

import java.sql.SQLException;
import java.util.List;

public class RealEstateService {
    private final RealEstateRepository realEstateRepository;
    public RealEstateService () {
        this.realEstateRepository = new RealEstateRepository();
    }

    public List<RealEstate> getAllRealEstate() throws SQLException {
        return realEstateRepository.findAll();
    }
}
