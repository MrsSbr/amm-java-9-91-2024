package ru.vsu.amm.java.service;

import org.jetbrains.annotations.Nullable;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.repository.TaskRepository;
import ru.vsu.amm.java.repository.ColumnRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class TaskService implements TaskServiceInterface{
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(UUID columnID, String taskTitle, @Nullable String taskDescription,
                           LocalDate startDate, @Nullable LocalDate endDate){
        Task task = new Task();
        task.setColumnID(columnID);
        task.setTaskTitle(taskTitle);
        if (taskDescription != null){
            task.setTaskDescription(taskDescription);
        }
        if (startDate != null){
            task.setStartDate(startDate);
        } else{
            task.setStartDate(LocalDate.now());
        }
        if (endDate != null){
            task.setEndDate(endDate);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task getTaskByID(UUID taskID){
        return taskRepository.getByID(taskID);
    }

    @Override
    public List<Task> getTasksByColumn(UUID columnID){
        return taskRepository.getByColumn(columnID);
    }

    @Override
    public Task updateTaskTitle(UUID taskID, String newTitle){
        Task task = taskRepository.getByID(taskID);
        if (task != null && newTitle != null){
            task.setTaskTitle(newTitle);
            taskRepository.update(task);
        }
        return task;
    }

    @Override
    public Task updateTaskDescription(UUID taskID, String newDescription){
        Task task = taskRepository.getByID(taskID);
        if (task != null){
            task.setTaskDescription(newDescription);
            taskRepository.update(task);
        }
        return task;
    }

    @Override
    public Task updateStartDate(UUID taskID, LocalDate newStartDate){
        Task task = taskRepository.getByID(taskID);
        if (task != null && newStartDate != null){
            task.setStartDate(newStartDate);
            taskRepository.update(task);
        }
        return task;
    }

    @Override
    public Task updateEndDate(UUID taskID, LocalDate newEndDate){
        Task task = taskRepository.getByID(taskID);
        if (task != null){
            task.setEndDate(newEndDate);
            taskRepository.update(task);
        }
        return task;
    }

    @Override
    public Task moveToColumn(UUID taskID, UUID newColumnID){
        Task task = taskRepository.getByID(taskID);
        if (task != null && newColumnID != null){
            task.setColumnID(newColumnID);
            taskRepository.update(task);
        }
        return task;
    }

    @Override
    public boolean deleteTask(UUID taskID){
        Task task = taskRepository.getByID(taskID);
        if (task != null){
            taskRepository.delete(taskID);
            return true;
        }
        return false;
    }

}
