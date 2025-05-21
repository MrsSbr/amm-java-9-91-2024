package ru.vsu.amm.java.services;

import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.enums.AchievementType;
import ru.vsu.amm.java.exceptions.AuthenticationException;
import ru.vsu.amm.java.repo.AchievementRepository;
import ru.vsu.amm.java.repo.EarnedAchievementRepository;
import ru.vsu.amm.java.repo.UserRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AuthenticationService {
    private final Logger logger = Logger.getLogger(AuthenticationService.class.getName());
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final EarnedAchievementRepository earnedAchievementRepository;

    public AuthenticationService(DataSource dataSource) {
        this.userRepository = new UserRepository(dataSource);
        this.achievementRepository = new AchievementRepository(dataSource);
        this.earnedAchievementRepository = new EarnedAchievementRepository(dataSource);
        logger.log(Level.INFO, "Authentication Service initialized");
    }

    public boolean login(String login, String password) {
        logger.log(Level.INFO, "Attempting to login");
        try {
            UserEntity authUser = userRepository.findByLogin(login);

            if (authUser == null) {
                logger.log(Level.SEVERE, "User not found");
                //throw new AuthenticationException("Login does not exist");
                return false;
            }
            logger.log(Level.INFO, "User found successfully");


            byte[] salt = Base64.getDecoder().decode(authUser.getSalt());
            PasswordHash hash = new PasswordHash();
            boolean pass = Arrays.equals(Base64.getDecoder().decode(authUser.getPasswordHash()), hash.encrypt(password, salt));
            if (pass) {
                logger.log(Level.INFO, "Successfully logged in");
            }
            authUser.setLoginCount(authUser.getLoginCount() + 1);
            userRepository.update(authUser);
            achievementRepository.insertAchievements(authUser.getId());
            if (authUser.getLoginCount() == 5) {
                Achievement loginAchievement = achievementRepository.findByType(AchievementType.LOGIN_COUNT)
                        .orElseThrow();

                earnedAchievementRepository.unlockAchievement(authUser.getId(), loginAchievement.getId());
            }
            return pass;

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean register(String login, String password, String email, String nickname, String phonenumber) {
        logger.log(Level.INFO, "Attempting to register");
        try {
            UserEntity authUser = userRepository.findByLogin(login);
            //UserEntity authUser = null;
            if (authUser == null) {
                logger.log(Level.INFO, "Existing user not found");
                PasswordHash hash = new PasswordHash();
                byte[][] passHashAndSalt = hash.encrypt(password);
                byte[] passwordHash = passHashAndSalt[0];
                byte[] salt = passHashAndSalt[1];
                authUser = new UserEntity(login,
                        nickname,
                        phonenumber,
                        Base64.getEncoder().encodeToString(passwordHash),
                        email,
                        Base64.getEncoder().encodeToString(salt));
                boolean saved = userRepository.save(authUser);
                if (saved) {
                    logger.log(Level.INFO, "Successfully registered");
                    AchievementRepository achievementRepository = new AchievementRepository(DatabaseAccess.getDataSource());
                    achievementRepository.insertAchievements(authUser.getId());
                }
                Achievement regAchievement = achievementRepository.findByType(AchievementType.REGISTRATION)
                        .orElseThrow();
                earnedAchievementRepository.unlockAchievement(authUser.getId(), regAchievement.getId());
                return saved;
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
