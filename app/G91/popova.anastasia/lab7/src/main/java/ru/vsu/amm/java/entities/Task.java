package ru.vsu.amm.java.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private UUID taskID;
    private UUID columnID;
    private String taskTitle;
    private String taskDescription;
    private LocalDate startDate;
    private LocalDate endDate;

    public Task() {
        this.taskID = UUID.randomUUID();
    };

    public UUID getTaskID() {
        return taskID;
    }

    public void setTaskID(UUID taskID) {
        this.taskID = taskID;
    }

    public UUID getColumnID() {
        return columnID;
    }

    public void setColumnID(UUID columnID) {
        this.columnID = columnID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
