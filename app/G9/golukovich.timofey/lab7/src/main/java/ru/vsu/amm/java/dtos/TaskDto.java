package ru.vsu.amm.java.dtos;

import java.time.LocalDateTime;

public class TaskDto {
    private Integer id;
    private String employeeLogin;
    private Integer hotelRoomId;
    private String managerLogin;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskDto(Integer id, String employeeLogin, Integer hotelRoomId, String managerLogin,
                   String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.employeeLogin = employeeLogin;
        this.hotelRoomId = hotelRoomId;
        this.managerLogin = managerLogin;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }

    public Integer getHotelRoomId() {
        return hotelRoomId;
    }

    public void setHotelRoomId(Integer hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
