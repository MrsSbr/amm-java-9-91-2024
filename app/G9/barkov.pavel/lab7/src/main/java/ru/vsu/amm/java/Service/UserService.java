package ru.vsu.amm.java.Service;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.Exeption.NotFoundException;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Repository.ShareholderRepository;
import ru.vsu.amm.java.Service.Convertors.ShareholderModelToEntity;
import ru.vsu.amm.java.Service.Entities.ShareholderCreateModel;
import ru.vsu.amm.java.Service.Interface.UserServiceInterface;

import java.sql.SQLException;
import java.util.logging.Logger;

public class UserService implements UserServiceInterface {
    private ShareholderRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService() {
        logger.info("Create service");
        userRepository = new ShareholderRepository();
    }

    @Override
    public boolean register(ShareholderCreateModel user, String password) throws SQLException {
        logger.info("Start registration");
        if (userRepository.getByEmail(user.email()).isEmpty()) {
            userRepository.create(ShareholderModelToEntity
                    .convert(user, BCrypt.hashpw(password, BCrypt.gensalt())));
            return true;
        } else {
            logger.severe("User already exists");
            return false;
        }
    }

    @Override
    public Shareholder login(String email, String password) throws SQLException {
        logger.info("Start login");
        Shareholder user = userRepository.getByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new UnCorrectDataException("Wrong password");
        return user;
    }
}
