package ru.vsu.amm.java.services;

import ru.vsu.amm.java.enams.PropertyTypeName;
import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.FoundPropertyRepository;
import ru.vsu.amm.java.repository.PropertyTypeRepository;
import ru.vsu.amm.java.enams.ReturnStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class FoundPropertyService {
    private FoundPropertyRepository foundPropertyRepository = new FoundPropertyRepository();
    private PropertyTypeRepository propertyTypeRepository = new PropertyTypeRepository();

    public void addFoundProperty(String propType, String placeOfFinding, String description, User user) {
        PropertyType propertyType = propertyTypeRepository.getByName(PropertyTypeName.valueOf(propType));
        propertyType.setPropertyTypeName(PropertyTypeName.valueOf(propType));

        FoundProperty property = new FoundProperty();
        property.setPropertyType(propertyType);
        property.setReturnStatus(ReturnStatus.NOT_RETURNED);
        property.setPlaceOfFinding(placeOfFinding);
        property.setDateOfFinding(LocalDate.now());
        property.setTimeOfFinding(LocalTime.now());
        property.setDescription(description);
        property.setUser(user);

        foundPropertyRepository.save(property);
    }

    public List<FoundProperty> getNotReturnedProperties() {
        List<FoundProperty> allProperties = foundPropertyRepository.getAll();

        for (FoundProperty property : allProperties) {
            PropertyType propertyType = propertyTypeRepository.getById(property.getPropertyType().getId());
            property.setPropertyType(propertyType);
        }

        return allProperties.stream()
                .filter(property -> property.getReturnStatus() == ReturnStatus.NOT_RETURNED)
                .collect(Collectors.toList());
    }

    public boolean updatePropertyStatusToReturned(long propertyId) {
        FoundProperty foundProperty = foundPropertyRepository.getById(propertyId);

        if (foundProperty != null) {
            foundProperty.setReturnStatus(ReturnStatus.RETURNED);
            foundPropertyRepository.update(foundProperty);
            return true;
        }
        return false;
    }
}
