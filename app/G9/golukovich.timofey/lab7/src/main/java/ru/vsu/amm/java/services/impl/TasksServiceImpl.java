package ru.vsu.amm.java.services.impl;

import ru.vsu.amm.java.dtos.TaskDto;
import ru.vsu.amm.java.entities.EmployeeEntity;
import ru.vsu.amm.java.entities.TaskEntity;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.exceptions.TaskNotFoundException;
import ru.vsu.amm.java.repository.EmployeeRepo;
import ru.vsu.amm.java.repository.HotelRoomRepo;
import ru.vsu.amm.java.repository.TaskRepo;
import ru.vsu.amm.java.services.TasksService;
import ru.vsu.amm.java.mappers.TaskDtoMapper;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TasksServiceImpl implements TasksService {
    private static final Logger logger = Logger.getLogger(TasksServiceImpl.class.getName());

    private final EmployeeRepo employeeRepo;
    private final HotelRoomRepo hotelRoomRepo;
    private final TaskRepo taskRepo;

    public TasksServiceImpl() {
        logger.info("EmployeesServiceImpl configuring");
        employeeRepo = new EmployeeRepo();
        hotelRoomRepo = new HotelRoomRepo();
        taskRepo = new TaskRepo();
    }

    @Override
    public List<TaskDto> getAllTasksByEmployeeId(int employeeId) {
        logger.info("HotelsServiceImpl get all tasks by employee login");

        try {
            var employeeOpt = employeeRepo.getById(employeeId);
            if (employeeOpt.isPresent()) {
                var taskEntities = taskRepo.getAllByEmployeeId(employeeId);

                var employeeLogin = employeeOpt.get().getLogin();
                List<TaskDto> taskDtos = new ArrayList<>();
                for (var task : taskEntities) {
                    var managerOpt = employeeRepo.getById(task.getManagerId());
                    if (managerOpt.isPresent()) {
                        var managerLogin = managerOpt.get().getLogin();

                        taskDtos.add(TaskDtoMapper.mapFromEntity(task, managerLogin, employeeLogin));
                    } else {
                        final String message = "Manager " + task.getManagerId() + " not found";
                        logger.info(message);
                        throw new EmployeeNotFoundException(message);
                    }
                }
                return taskDtos;
            } else {
                final String message = "Employee " + employeeId + " not found";
                logger.info(message);
                throw new EmployeeNotFoundException(message);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public TaskDto createTask(Integer managerId, int employeeId, int hotelRoomId, String description) {
        logger.info("create task");

        try {
            var manager = getManager(managerId);
            var employee = getEmployee(employeeId, manager);
            checkHotelRoomById(hotelRoomId);

            var taskEntity = new TaskEntity(0, employeeId, hotelRoomId, managerId, description, LocalDateTime.now(), LocalDateTime.now());
            taskEntity = taskRepo.save(taskEntity);

            return TaskDtoMapper.mapFromEntity(taskEntity, manager.getLogin(), employee.getLogin());
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public TaskDto updateTask(int managerId, int taskId, Integer updatedManagerId, int updatedEmployeeId, int updatedHotelRoomId, String updatedDescription) {
        logger.info("edit task " + taskId);

        try {
            var manager = getManager(managerId);

            var taskEntity = getTask(taskId);

            checkManagerPost(manager, taskEntity.getManagerId());

            var updatedManager = getManager(updatedManagerId);
            var updatedEmployee = getEmployee(updatedEmployeeId, updatedManager);
            checkHotelRoomById(updatedHotelRoomId);

            taskEntity.setManagerId(updatedManagerId);
            taskEntity.setEmployeeId(updatedEmployeeId);
            taskEntity.setHotelRoomId(updatedHotelRoomId);
            taskEntity.setDescription(updatedDescription);
            taskEntity.setUpdatedAt(LocalDateTime.now());
            taskRepo.update(taskEntity);

            return TaskDtoMapper.mapFromEntity(taskEntity, updatedManager.getLogin(), updatedEmployee.getLogin());
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void removeTask(int managerId, int taskId) {
        logger.info("remove task " + taskId);

        try {
            var manager = getManager(managerId);

            var taskEntity = getTask(taskId);

            checkManagerPost(manager, taskEntity.getManagerId());

            taskRepo.delete(taskId);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    private EmployeeEntity getManager(Integer managerId) throws SQLException {
        EmployeeEntity managerEntity;

        if (managerId == null) {
            managerEntity = null;
        } else {
            var managerEntityOpt = employeeRepo.getById(managerId);
            if (managerEntityOpt.isPresent()) {
                managerEntity = managerEntityOpt.get();
                if (managerEntity.getPost() == EmployeePost.STAFF) {
                    final String message = "Employee " + managerEntity.getLogin() + " can't own tasks";
                    logger.info(message);
                    throw new NotAllowedActionException(message);
                }
            } else {
                final String message = "Manager not found by id " + managerId;
                logger.info(message);
                throw new EmployeeNotFoundException(message);
            }
        }

        return managerEntity;
    }

    private EmployeeEntity getEmployee(int employeeId, EmployeeEntity manager) throws SQLException {
        EmployeeEntity employeeEntity;

        var employeeEntityOpt = employeeRepo.getById(employeeId);
        if (employeeEntityOpt.isPresent()) {
            employeeEntity = employeeEntityOpt.get();
        } else {
            final String message = "Employee not found by id " + employeeId;
            logger.info(message);
            throw new EmployeeNotFoundException(message);
        }

        if (manager != null && manager.getPost().compareTo(employeeEntity.getPost()) <= 0) {
            final String message = "Employee " + manager.getLogin() + " can't own " + employeeEntity.getLogin() + " tasks";
            logger.info(message);
            throw new NotAllowedActionException(message);
        }

        return employeeEntity;
    }

    private TaskEntity getTask(int taskId) throws SQLException {
        var taskEntityOpt = taskRepo.getById(taskId);
        if (taskEntityOpt.isEmpty()) {
            final String message = "Task not found by id " + taskId;
            logger.info(message);
            throw new TaskNotFoundException(message);
        }
        return taskEntityOpt.get();
    }

    private void checkManagerPost(EmployeeEntity manager, Integer currentManagerId) throws SQLException {
        if (currentManagerId != null) {
            EmployeeEntity currentTaskManager;

            try {
                currentTaskManager = getManager(currentManagerId);
            } catch (EmployeeNotFoundException e) {
                currentTaskManager = null;
            }

            if (currentTaskManager != null && manager.getPost().compareTo(currentTaskManager.getPost()) < 0) {
                final String message = "Manager " + manager.getLogin() + " can't edit this task, while " + currentTaskManager.getLogin() + " is owner";
                logger.info(message);
                throw new NotAllowedActionException(message);
            }
        }
    }

    private void checkHotelRoomById(int hotelRoomId) throws SQLException {
        var hotelRoomEntityOpt = hotelRoomRepo.getById(hotelRoomId);
        if (hotelRoomEntityOpt.isEmpty()) {
            final String message = "Hotel room not found by id " + hotelRoomId;
            logger.info(message);
            throw new EmployeeNotFoundException(message);
        }
    }
}
