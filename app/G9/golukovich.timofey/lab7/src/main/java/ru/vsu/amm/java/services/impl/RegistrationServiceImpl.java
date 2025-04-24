package ru.vsu.amm.java.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.HotelNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.exceptions.PostDoesNotExistException;
import ru.vsu.amm.java.repository.EmployeeRepo;
import ru.vsu.amm.java.repository.HotelRepo;
import ru.vsu.amm.java.services.RegistrationService;

import java.sql.SQLException;
import java.util.logging.Logger;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class.getName());

    private final EmployeeRepo employeeRepo;
    private final HotelRepo hotelRepo;

    public RegistrationServiceImpl() {
        logger.info("RegistrationServiceImpl configuring");
        employeeRepo = new EmployeeRepo();
        hotelRepo = new HotelRepo();
    }

    @Override
    public void registerEmployee(String login, String password, String rawPost, Integer hotelId, EmployeeDto adminDto) {
        logger.info("register employee " + login);

        try {
            var post = EmployeePost.valueOf(rawPost);
            if (adminDto.getPost().compareTo(post) > 0) {
                var hotelOpt = hotelRepo.getById(hotelId);
                if (hotelOpt.isPresent())
                {
                    if (employeeRepo.getByLogin(login).isEmpty()) {
                        password = BCrypt.hashpw(password, BCrypt.gensalt());
                        employeeRepo.save(login, password, post, hotelId);
                        logger.info("Employee " + login + " was registered");
                    } else {
                        final String message = "Employee " + login + " already exists";
                        logger.info(message);
                        throw new EmployeeAlreadyExistsException(message);
                    }
                } else {
                    final String message = "Hotel " + hotelId + " does not exists";
                    logger.info(message);
                    throw new HotelNotFoundException(message);
                }
            } else {
                final String message = "Employee " + adminDto.getLogin() + " can't register employee " + login;
                logger.info(message);
                throw new NotAllowedActionException(message);
            }
        } catch (IllegalArgumentException e) {
            final String message = "Post " + rawPost + " does not exist";
            logger.info(message);
            throw new PostDoesNotExistException(message);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void unregisterEmployee(String login, EmployeeDto adminDto) {
        logger.info("unregister employee " + login);

        try {
            var employeeOpt = employeeRepo.getByLogin(login);
            if (employeeOpt.isPresent()) {
                var employee = employeeOpt.get();
                if (adminDto.getPost().compareTo(employee.getPost()) > 0) {
                    employeeRepo.delete(employee.getId());
                    logger.info("Employee " + login + " was unregistered");
                } else {
                    final String message = "Employee " + adminDto.getLogin() + " can't unregister employee " + login;
                    logger.info(message);
                    throw new NotAllowedActionException(message);
                }
            } else {
                final String message = "Employee " + login + " does not exists";
                logger.info(message);
                throw new EmployeeAlreadyExistsException(message);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
