package org.example.cli.department;

import org.example.cli.Command;
import org.example.entities.Department;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

public class GetAllDepartments implements Command {
    private final DepartmentService departmentService;

    public GetAllDepartments() {
        this.departmentService = DepartmentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("Список всех кафедр:");
        if (!departmentService.getAll().isEmpty()) {
            for (Department dep : departmentService.getAll()) {
                System.out.println(dep);
            }
        } else {
            System.out.println("Нет ни одной кафедры.");
        }

    }

    @Override
    public String getCommandName() {
        return "Get all departments";
    }
}
