package ru.vsu.amm.java.service.implementations;


import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.UserRole;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.UserRequest;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.interfaces.AuthService;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import ru.vsu.amm.java.utils.ErrorMessages;

public class UserAuthManager implements AuthService {
    private final UserRepository userRepository;
    private final BcryptPasswordEncoder passwordEncoder;

    public UserAuthManager() {
        this.passwordEncoder = new BcryptPasswordEncoder(12);
        this.userRepository = new UserRepository();
    }

    public UserAuthManager(UserRepository userRepository, BcryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRole login(UserRequest request) {
        UserEntity user = userRepository.findByName(request.name())
                .orElseThrow(() -> new WrongUserCredentialsException(ErrorMessages.USER_NOT_FOUND));

        performPasswordCheck(user.getPassword(), request.password());
        return user.getUserRole();
    }

    @Override
    public void register(UserRequest request) {
        checkUserExistence(request.name());
        UserEntity userEntity = new UserEntity(null, request.name(),
                    passwordEncoder.hashPassword(request.password()), UserRole.USER);
        userRepository.save(userEntity);
    }

    private void checkUserExistence(String username) {
        if (userRepository.findByName(username).isPresent()) {
            throw new WrongUserCredentialsException(ErrorMessages.USER_ALREADY_EXISTS);
        }
    }

    private void performPasswordCheck(String storedHash, String rawPassword) {
        if (!passwordEncoder.checkPassword(rawPassword, storedHash)) {
            throw new WrongUserCredentialsException(ErrorMessages.INCORRECT_PASSWORD);
        }
    }
}