package ru.vsu.amm.java.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public void addUser(String nickname, String password, List<Role> roles) {
        UserEntity user = new UserEntity();
        user.setNickname(nickname);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRating(1000d);
        user.setRoles(roles);

        userRepository.create(user);
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    public List<UserEntity> getTopRatedUsers(long count) {
        List<UserEntity> users = userRepository.findTopRated(count);
        return users;
    }

    public UserEntity getUserByNickname(String nickname) {
        UserEntity user = userRepository.findByNickname(nickname);
        return user;
    }
}
