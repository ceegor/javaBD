package org.example.cli.Get;

import org.example.cli.Command;
import org.example.entities.Department;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

public class getAllDepartments implements Command {
    private final DepartmentService departmentService;

    public getAllDepartments() {
        this.departmentService = DepartmentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("Список всех кафедр:");
        for (Department dep : departmentService.getAll()) {
            System.out.println(dep);
        }
    }

    @Override
    public String getCommandName() {
        return "Get all departments";
    }
}
