package org.example.cli.department;

import org.example.cli.Command;
import org.example.entities.Department;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

import java.util.Scanner;

public class GetDepartmentById implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final DepartmentService departmentService;

    public GetDepartmentById() {
        this.departmentService = DepartmentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите ID кафедры: ");
        int id = scn.nextInt();
        scn.nextLine();
        Department dep = departmentService.getById(id);
        if (dep != null) {
            System.out.println("Найдена кафедра: " + dep);
        } else {
            System.out.println("Кафедра с ID " + id + " не найдена.");
        }
    }

    @Override
    public String getCommandName() {
        return "Get department by id";
    }
}
