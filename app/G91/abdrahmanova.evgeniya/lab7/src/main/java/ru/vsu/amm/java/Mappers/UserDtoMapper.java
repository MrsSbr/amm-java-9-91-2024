package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Dtos.UserDto;
import ru.vsu.amm.java.Entities.User;

public class UserDtoMapper {
    public static UserDto mapFromEntity(User e) {
        return new UserDto(e.getId(), e.getFullName(), e.getBirthday(), e.geteMail(), e.getNumberPhone(), e.getUserRole());
    }
}
