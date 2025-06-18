package ru.vsu.amm.java.service;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.repository.TaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class TaskService implements TaskServiceInterface {
    private static final Logger log = LoggerFactory.getLogger((TaskService.class));

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(UUID columnID, String taskTitle, @Nullable String taskDescription,
                           LocalDate startDate, @Nullable LocalDate endDate) {
        log.debug("Creating task for column: {}, title: {}", columnID, taskTitle);
        try{
            Task task = new Task();
            task.setTaskID(UUID.randomUUID());
            task.setColumnID(columnID);
            task.setTaskTitle(taskTitle);
            if (taskDescription != null) {
                task.setTaskDescription(taskDescription);
            }
            if (startDate != null) {
                task.setStartDate(startDate);
            } else{
                task.setStartDate(LocalDate.now());
            }
            if (endDate != null) {
                task.setEndDate(endDate);
            }
            taskRepository.save(task);
            log.info("Created task successfully: {}", task.getTaskID());
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to create task for column: {}, title: {}", columnID, taskTitle, e);
            throw e;
        }
    }

    @Override
    public Task getTaskByID(UUID taskID) {
        log.debug("Fetching task with ID: {}", taskID);
        try {
            Task task = taskRepository.getByID(taskID);
            log.info("Fetched task successfully: {}", taskID);
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to fetch task with ID: {}", taskID, e);
            throw e;
        }
    }

    @Override
    public List<Task> getTasksByColumn(UUID columnID) {
        log.debug("Fetching tasks for column: {}", columnID);
        try {
            List<Task> tasks = taskRepository.getByColumn(columnID);
            log.info("Fetched {} tasks for column successfully: {}", tasks.size(), columnID);
            return tasks;
        } catch (RuntimeException e) {
            log.error("Failed to fetch tasks for column: {}", columnID, e);
            throw e;
        }
    }

    @Override
    public Task updateTaskTitle(UUID taskID, String newTitle) {
        log.debug("Updating title for task: {}, newTitle: {}", taskID, newTitle);
        try {
            Task task = taskRepository.getByID(taskID);
            if (task != null && newTitle != null){
                task.setTaskTitle(newTitle);
                taskRepository.update(task);
                log.info("Updated title for task successfully: {}", taskID);
            }
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to update title for task: {}", taskID, e);
            throw e;
        }
    }

    @Override
    public Task updateTaskDescription(UUID taskID, String newDescription) {
        log.debug("Updating description for task: {}, newDescription: {}", taskID, newDescription);
        try {
            Task task = taskRepository.getByID(taskID);
            if (task != null) {
                task.setTaskDescription(newDescription);
                taskRepository.update(task);
                log.info("Updated description for task successfully: {}", taskID);
            }
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to update description for task: {}", taskID, e);
            throw e;
        }
    }

    @Override
    public Task updateStartDate(UUID taskID, LocalDate newStartDate) {
        log.debug("Updating start date for task: {}, newStartDate: {}", taskID, newStartDate);
        try {
            Task task = taskRepository.getByID(taskID);
            if (task != null && newStartDate != null) {
                task.setStartDate(newStartDate);
                taskRepository.update(task);
                log.info("Updated start date for task successfully: {}", taskID);
            }
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to update start date for task: {}", taskID, e);
            throw e;
        }
    }

    @Override
    public Task updateEndDate(UUID taskID, LocalDate newEndDate) {
        log.debug("Updating end date for task: {}, newStartDate: {}", taskID, newEndDate);
        try {
            Task task = taskRepository.getByID(taskID);
            if (task != null) {
                task.setEndDate(newEndDate);
                taskRepository.update(task);
                log.info("Updated end date for task successfully: {}", taskID);
            }
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to update end date for task: {}", taskID, e);
            throw e;
        }
    }

    @Override
    public Task moveToColumn(UUID taskID, UUID newColumnID) {
        log.debug("Moving task: {} to column: {}", taskID, newColumnID);
        try {
            Task task = taskRepository.getByID(taskID);
            if (task != null && newColumnID != null) {
                task.setColumnID(newColumnID);
                taskRepository.update(task);
                log.info("Moved task: {} to column: {} successfully", taskID, newColumnID);
            }
            return task;
        } catch (RuntimeException e) {
            log.error("Failed to move task: {} to column: {}", taskID, newColumnID, e);
            throw e;
        }
    }

    @Override
    public void deleteTask(UUID taskID) {
        log.debug("Deleting task: {}", taskID);
        try {
            taskRepository.delete(taskID);
            log.info("Deleted task successfully: {}", taskID);
        } catch (RuntimeException e) {
            log.error("Failed to delete task: {}", taskID, e);
            throw e;
        }
    }

}
