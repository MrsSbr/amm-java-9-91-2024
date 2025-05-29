package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Task;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskServiceInterface {

    Task createTask(UUID columnID, String taskTitle, @Nullable String taskDescription,
                    LocalDate startDate, @Nullable LocalDate endDate);
    Task getTaskByID(UUID taskID);
    List<Task> getTasksByColumn(UUID columnID);
    Task updateTaskTitle(UUID taskID, String newTitle);
    Task updateTaskDescription(UUID taskID, String newDescription);
    Task updateStartDate(UUID taskID, LocalDate newStartDate);
    Task updateEndDate(UUID taskID, LocalDate newEndDate);
    Task moveToColumn(UUID taskID, UUID newColumnID);
    void deleteTask(UUID taskID);

}
