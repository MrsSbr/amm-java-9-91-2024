package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.enums.EmployeePost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePostActionsFilterPaths {
    public static Map<String, List<EmployeePost>> getRequired() {
        var paths = new HashMap<String, List<EmployeePost>>();

        paths.put("/api/main", List.of(EmployeePost.STAFF, EmployeePost.MANAGER, EmployeePost.ADMINISTRATOR, EmployeePost.MASTER_ADMINISTRATOR));
        paths.put("/api/profile", List.of(EmployeePost.STAFF, EmployeePost.MANAGER, EmployeePost.ADMINISTRATOR, EmployeePost.MASTER_ADMINISTRATOR));
        paths.put("/api/tasks_dashboard", List.of(EmployeePost.STAFF));
        paths.put("/api/manage_tasks", List.of(EmployeePost.MANAGER));
        paths.put("/api/create_task", List.of(EmployeePost.MANAGER));
        paths.put("/api/edit_task", List.of(EmployeePost.MANAGER));
        paths.put("/api/remove_task", List.of(EmployeePost.MANAGER));
        paths.put("/api/hotels_management", List.of(EmployeePost.MASTER_ADMINISTRATOR));
        paths.put("/api/hotel_rooms_management", List.of(EmployeePost.ADMINISTRATOR, EmployeePost.MASTER_ADMINISTRATOR));
        paths.put("/api/employees_dashboard", List.of(EmployeePost.MANAGER, EmployeePost.ADMINISTRATOR, EmployeePost.MASTER_ADMINISTRATOR));
        paths.put("/api/register_employee", List.of(EmployeePost.ADMINISTRATOR, EmployeePost.MASTER_ADMINISTRATOR));
        paths.put("/api/unregister_employee", List.of(EmployeePost.ADMINISTRATOR, EmployeePost.MASTER_ADMINISTRATOR));

        return paths;
    }
}
