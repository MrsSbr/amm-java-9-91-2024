package ru.vsu.amm.java.services.impl;

import ru.vsu.amm.java.dtos.TaskDto;
import ru.vsu.amm.java.entities.EmployeeEntity;
import ru.vsu.amm.java.entities.TaskEntity;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.enums.TaskStatus;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.exceptions.TaskNotFoundException;
import ru.vsu.amm.java.exceptions.TaskStatusDoesNotExistException;
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
            var employeeOpt = employeeRepo.getById(employeeId, false);
            if (employeeOpt.isPresent()) {
                var taskEntities = taskRepo.getAllByEmployeeId(employeeId, false);

                var employeeLogin = employeeOpt.get().getLogin();
                List<TaskDto> taskDtos = new ArrayList<>();
                for (var task : taskEntities) {
                    var managerOpt = employeeRepo.getById(task.getManagerId(), false);
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
    public TaskDto createTask(Integer managerId, String rawEmployeeId, String rawHotelRoomId, String description) {
        logger.info("create task");

        try {
            var employeeId = Integer.parseInt(rawEmployeeId);
            var hotelRoomId = Integer.parseInt(rawHotelRoomId);

            var manager = getManager(managerId, true);
            var employee = getEmployee(employeeId, manager, true);
            checkHotelRoomById(hotelRoomId);

            var status = TaskStatus.TODO;
            var taskEntity = new TaskEntity(0, employeeId, hotelRoomId, managerId, status, description, LocalDateTime.now(), LocalDateTime.now());
            taskEntity = taskRepo.save(taskEntity);

            return TaskDtoMapper.mapFromEntity(taskEntity, manager.getLogin(), employee.getLogin());
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } catch (NumberFormatException e) {
            final String errorMessage = "Id must be number";
            logger.info(errorMessage);
            throw new NumberFormatException(errorMessage);
        }
    }

    @Override
    public TaskDto updateTask(int managerId, int taskId, String updatedManagerLogin, String updatedEmployeeLogin, int updatedHotelRoomId,
                              String updatedStatusName, String updatedDescription) {
        logger.info("update task " + taskId);

        try {
            var updatedStatus = TaskStatus.valueOf(updatedStatusName);

            var manager = getManager(managerId, false);

            var taskEntity = getTask(taskId, true);

            checkManagerPost(manager, taskEntity.getManagerId());

            var updatedManager = getManager(updatedManagerLogin, true);
            var updatedEmployee = getEmployee(updatedEmployeeLogin, updatedManager, true);
            checkHotelRoomById(updatedHotelRoomId);

            taskEntity.setManagerId(updatedManager.getId());
            taskEntity.setEmployeeId(updatedEmployee.getId());
            taskEntity.setHotelRoomId(updatedHotelRoomId);
            taskEntity.setStatus(updatedStatus);
            taskEntity.setDescription(updatedDescription);
            taskEntity.setUpdatedAt(LocalDateTime.now());
            taskRepo.update(taskEntity);

            return TaskDtoMapper.mapFromEntity(taskEntity, updatedManager.getLogin(), updatedEmployee.getLogin());
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } catch (IllegalArgumentException e) {
            final String message = "Task status " + updatedStatusName + " does not exist";
            logger.info(message);
            throw new TaskStatusDoesNotExistException(message);
        }
    }

    @Override
    public void removeTask(int managerId, int taskId, int employeeId) {
        logger.info("remove task " + taskId);

        try {
            var manager = getManager(managerId, false);

            var taskEntity = getTask(taskId, true);

            if (taskEntity.getEmployeeId() != employeeId) {
                throw new EmployeeNotFoundException("Employee " + employeeId + " can't do task " + taskId);
            }

            checkManagerPost(manager, taskEntity.getManagerId());

            taskRepo.delete(taskId);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    private EmployeeEntity getManager(Integer managerId, boolean isForUpdate) throws SQLException {
        EmployeeEntity managerEntity;

        if (managerId == null) {
            managerEntity = null;
        } else {
            var managerEntityOpt = employeeRepo.getById(managerId, isForUpdate);
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

    private EmployeeEntity getManager(String managerLogin, boolean isForUpdate) throws SQLException {
        EmployeeEntity managerEntity;

        if (managerLogin == null) {
            managerEntity = null;
        } else {
            var managerEntityOpt = employeeRepo.getByLogin(managerLogin, isForUpdate);
            if (managerEntityOpt.isPresent()) {
                managerEntity = managerEntityOpt.get();
                if (managerEntity.getPost() == EmployeePost.STAFF) {
                    final String message = "Employee " + managerEntity.getLogin() + " can't own tasks";
                    logger.info(message);
                    throw new NotAllowedActionException(message);
                }
            } else {
                final String message = "Manager not found by login " + managerLogin;
                logger.info(message);
                throw new EmployeeNotFoundException(message);
            }
        }

        return managerEntity;
    }

    private EmployeeEntity getEmployee(int employeeId, EmployeeEntity manager, boolean isForUpdate) throws SQLException {
        EmployeeEntity employeeEntity;

        var employeeEntityOpt = employeeRepo.getById(employeeId, isForUpdate);
        if (employeeEntityOpt.isPresent()) {
            employeeEntity = employeeEntityOpt.get();
        } else {
            final String message = "Employee not found by id " + employeeId;
            logger.info(message);
            throw new EmployeeNotFoundException(message);
        }

        CheckEmployeePermission(employeeEntity, manager);

        return employeeEntity;
    }

    private EmployeeEntity getEmployee(String employeeLogin, EmployeeEntity manager,
                                       boolean isForUpdate) throws SQLException {
        EmployeeEntity employeeEntity;

        var employeeEntityOpt = employeeRepo.getByLogin(employeeLogin, isForUpdate);
        if (employeeEntityOpt.isPresent()) {
            employeeEntity = employeeEntityOpt.get();
        } else {
            final String message = "Employee not found by login " + employeeLogin;
            logger.info(message);
            throw new EmployeeNotFoundException(message);
        }

        CheckEmployeePermission(employeeEntity, manager);

        return employeeEntity;
    }

    private void CheckEmployeePermission(EmployeeEntity employee, EmployeeEntity manager) {
        if (manager != null && manager.getPost().compareTo(employee.getPost()) < 0) {
            final String message = "Employee " + manager.getLogin() + " can't own " + employee.getLogin() + " tasks";
            logger.info(message);
            throw new NotAllowedActionException(message);
        }
    }

    private TaskEntity getTask(int taskId, boolean isForUpdate) throws SQLException {
        var taskEntityOpt = taskRepo.getById(taskId, isForUpdate);
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
                currentTaskManager = getManager(currentManagerId, false);
            } catch (EmployeeNotFoundException e) {
                currentTaskManager = null;
            }

            if (currentTaskManager != null && manager.getPost().compareTo(currentTaskManager.getPost()) < 0) {
                final String message = "Manager " + manager.getLogin() + " can't update this task, while " + currentTaskManager.getLogin() + " is owner";
                logger.info(message);
                throw new NotAllowedActionException(message);
            }
        }
    }

    private void checkHotelRoomById(int hotelRoomId) throws SQLException {
        var hotelRoomEntityOpt = hotelRoomRepo.getById(hotelRoomId, false);
        if (hotelRoomEntityOpt.isEmpty()) {
            final String message = "Hotel room not found by id " + hotelRoomId;
            logger.info(message);
            throw new EmployeeNotFoundException(message);
        }
    }
}
