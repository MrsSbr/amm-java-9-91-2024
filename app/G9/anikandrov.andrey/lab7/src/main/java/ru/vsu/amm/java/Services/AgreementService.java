package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.AgreementEntity;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.AgreementRepository;
import ru.vsu.amm.java.Repository.RentalObjectRepository;
import ru.vsu.amm.java.Repository.UserRepository;
import ru.vsu.amm.java.Exception.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgreementService {

    private static final Logger logger = Logger.getLogger(AgreementService.class.getName());

    private final AgreementRepository agreementRepo;
    private final UserRepository userRepo;
    private final RentalObjectRepository objectRepo;

    public AgreementService(AgreementRepository agreementRepo,
                            UserRepository userRepo,
                            RentalObjectRepository objectRepo) {
        this.agreementRepo = agreementRepo;
        this.userRepo = userRepo;
        this.objectRepo = objectRepo;
    }

    public void createAgreement(String username, Long objectId,
                                LocalDate startDate, LocalDate endDate) throws Exception {
        logger.log(Level.INFO, "Attempting to create agreement for user: " + username);

        validateDates(startDate, endDate);

        Long userId = userRepo.findByUserName(username)
                .orElseThrow(() -> {
                    logger.log(Level.SEVERE, "User not found: " + username);
                    return new Exception("User not found");
                })
                .getUserID();

        int price = objectRepo.findById(objectId)
                .orElseThrow(() -> {
                    logger.log(Level.SEVERE, "Object not found: " + objectId);
                    return new Exception("Object not found");
                })
                .getPrice();

        int days = (int) (endDate.toEpochDay() - startDate.toEpochDay());
        int totalPrice = price * days;

        AgreementEntity agreement = new AgreementEntity();
        agreement.setUserID(userId);
        agreement.setObjectID(objectId);
        agreement.setTimeStart(startDate);
        agreement.setTimeEnd(endDate);
        agreement.setSumPrice(totalPrice);

        agreementRepo.save(agreement);
        logger.log(Level.INFO, "Agreement created: ID=" + agreement.getAgreementID());
    }

    private void validateDates(LocalDate start, LocalDate end) throws ValidationException {
        logger.log(Level.FINE, "Dates validation: " + start + " - " + end);

        if (start.isAfter(end)) {
            logger.log(Level.WARNING, "Start date cannot be later than end date");
            throw new ValidationException("Start date cannot be later than end date");
        }
        if (start.isBefore(LocalDate.now())) {
            logger.log(Level.WARNING, "Start date cannot be in the past");
            throw new ValidationException("Start date cannot be in the past");
        }
    }

    public List<AgreementEntity> getUserAgreements(String username) throws NotFoundException, DatabaseException {
        try {
            logger.log(Level.INFO, "Get agreements for user: " + username);


            Long userId = userRepo.findByUserName(username)
                    .orElseThrow(() -> {
                        logger.log(Level.SEVERE, "User not found: " + username);
                        return new NotFoundException("User not found");
                    })
                    .getUserID();

            List<AgreementEntity> allAgreements = agreementRepo.findAll();
            List<AgreementEntity> userAgreements = new ArrayList<>();

            for (AgreementEntity agreement : allAgreements) {
                if (agreement.getUserID().equals(userId)) {
                    userAgreements.add(agreement);
                }
            }

            logger.log(Level.INFO, "Find " + userAgreements.size() + " agreements");
            return userAgreements;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error with getting user agreements");
            throw new DatabaseException("Error with getting user agreements");
        }
    }
}
