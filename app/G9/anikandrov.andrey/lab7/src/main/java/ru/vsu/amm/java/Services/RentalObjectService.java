package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Enums.ObjectType;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Repository.RentalObjectRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RentalObjectService {
    private static final Logger logger = Logger.getLogger(RentalObjectService.class.getName());

    private final RentalObjectRepository repository;

    public RentalObjectService(RentalObjectRepository repository) {
        this.repository = repository;
    }

    public List<RentalObjectEntity> getObjectsByType(ObjectType type) throws DatabaseException {
        logger.log(Level.INFO, "Getting objects by type: " + type);

        try {
            return repository.findAll().stream()
                    .filter(o -> o.getObjectType() == type)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new DatabaseException("Error with getting objects by type");
        }
    }

    public List<RentalObjectEntity> getObjectsForAgreements(List<Long> objectIds) throws DatabaseException {
        logger.log(Level.INFO, "Fetching objects for agreements");

        try {
            List<RentalObjectEntity> objects = new ArrayList<>();
            for (Long id : objectIds) {
                repository.findById(id).ifPresent(objects::add);
            }
            return objects;
        } catch (Exception e) {
            throw new DatabaseException("Error with getting rental objects");
        }
    }
}