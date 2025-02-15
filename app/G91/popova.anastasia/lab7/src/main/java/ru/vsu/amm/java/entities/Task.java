package ru.vsu.amm.java.entities;

import java.util.UUID;

public class Task {

    private UUID taskID;
    private UUID columnID;
    private String taskTitle;
    private String taskUrgency;
    private String taskDescription;

    public Task() {};

    private UUID getTaskID() {
        return taskID;
    }

    private void setTaskID(UUID taskID) {
        this.taskID = taskID;
    }

    private UUID getColumnID() {
        return columnID;
    }

    private void setColumnID(UUID columnID) {
        this.columnID = columnID;
    }

    private String getTaskTitle() {
        return taskTitle;
    }

    private void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    private String getTaskUrgency() {
        return taskUrgency;
    }

    private void setTaskUrgency(String taskUrgency) {
        this.taskUrgency = taskUrgency;
    }

    private String getTaskDescription() {
        return taskDescription;
    }

    private void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
