package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.dtos.TaskDto;
import ru.vsu.amm.java.entities.TaskEntity;

public class TaskDtoMapper {
    public static TaskDto mapFromEntity(TaskEntity entity) {
        return new TaskDto(entity.getId(), "Employee id: " + entity.getEmployeeId(), entity.getHotelRoomId(),
                "Manager id: " + (entity.getManagerId() == null ? "-" : entity.getManagerId()),
                entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TaskDto mapFromEntity(TaskEntity entity, String managerLogin, String employeeLogin) {
        return new TaskDto(entity.getId(), employeeLogin, entity.getHotelRoomId(),
                managerLogin, entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
