package org.example.cli.Get.department;

import org.example.cli.Command;
import org.example.entities.Department;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

import java.util.Scanner;

public class DeleteDepartmentById implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final DepartmentService departmentService;

    public DeleteDepartmentById() {
        this.departmentService = DepartmentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите ID кафедры для удаления: ");
        int id = scn.nextInt();
        scn.nextLine();

        Department dep = departmentService.getById(id);
        if (dep == null) {
            System.out.println("Кафедра с таким ID не найдена.");
            return;
        }

        departmentService.delete(id);
        System.out.println("Кафедра удалена: " + dep);
    }

    @Override
    public String getCommandName() {
        return "Delete department by id";
    }
}
