package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.TaskDto;

import java.util.List;

public interface TasksService {
    List<TaskDto> getAllTasksByEmployeeId(int employeeId);
    TaskDto createTask(Integer managerId, String employeeId, String hotelRoomId, String description);
    TaskDto updateTask(int managerId, int taskId, String updatedManagerLogin, String updatedEmployeeLogin, int updatedHotelRoomId, String updatedStatusName, String updatedDescription);
    void removeTask(int managerId, int taskId, int employeeId);
}
