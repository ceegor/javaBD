package org.example.cli.Get.department;

import org.example.cli.Command;
import org.example.entities.Department;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

import java.util.Scanner;

public class CreateDepartment implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final DepartmentService departmentService;

    public CreateDepartment() {
        this.departmentService = DepartmentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите название кафедры: ");
        String name = scn.nextLine();

        System.out.print("Введите ID факультета: ");
        String facultyId = scn.nextLine();
        scn.nextLine();

        String[] parameters = { name, facultyId };
        Department dep = departmentService.createDepartment(parameters);
        System.out.println("Создана кафедра: " + dep);
    }

    @Override
    public String getCommandName() {
        return "Create new department";
    }
}
