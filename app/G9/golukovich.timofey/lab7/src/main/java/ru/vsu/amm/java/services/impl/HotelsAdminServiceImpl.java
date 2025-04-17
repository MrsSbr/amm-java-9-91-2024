package ru.vsu.amm.java.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.PostDoesNotExistException;
import ru.vsu.amm.java.repository.EmployeeRepo;
import ru.vsu.amm.java.services.HotelsAdminService;

import java.sql.SQLException;
import java.util.logging.Logger;

public class HotelsAdminServiceImpl implements HotelsAdminService {
    private static final Logger logger = Logger.getLogger(HotelsAdminServiceImpl.class.getName());

    private final EmployeeRepo employeeRepo;

    public HotelsAdminServiceImpl() {
        logger.info("HotelsAdminServiceImpl configuring");
        employeeRepo = new EmployeeRepo();
    }

    @Override
    public void registerEmployee(String login, String password, String post) {
        logger.info("register user" + login);

        try {
            EmployeePost.valueOf(post);

            if (employeeRepo.getByLogin(login).isEmpty()) {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                employeeRepo.save(login, password, EmployeePost.valueOf(post));
            } else {
                final String message = "Employee " + login + " already exists";
                logger.info(message);
                throw new EmployeeAlreadyExistsException(message);
            }
        } catch (IllegalArgumentException e) {
            final String message = "Post " + post + " does not exist";
            logger.info(message);
            throw new PostDoesNotExistException(message);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
