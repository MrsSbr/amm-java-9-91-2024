package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.TaskDto;

import java.util.List;

public interface TasksService {
    List<TaskDto> getAllTasksByEmployeeId(int employeeId);
    TaskDto createTask(Integer managerId, int employeeId, int hotelRoomId, String description);
    TaskDto updateTask(int managerId, int taskId, Integer updatedManagerId, int updatedEmployeeId, int updatedHotelRoomId, String updatedDescription);
    void removeTask(int managerId, int taskId);
}
