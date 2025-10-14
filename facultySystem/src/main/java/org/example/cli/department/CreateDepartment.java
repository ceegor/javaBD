package org.example.cli.department;

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
        String name;
        while (true) {
            System.out.print("Введите название кафедры: ");
            name = scn.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Название не может быть пустым. Попробуйте снова.");
            } else {
                break;
            }
        }

        String facultyId;
        while (true) {
            System.out.print("Введите ID факультета (число): ");
            facultyId = scn.nextLine().trim();

            if (facultyId.isEmpty()) {
                System.out.println("ID не может быть пустым. Попробуйте снова.");
                continue;
            }

            try {
                Integer.parseInt(facultyId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }

        String[] parameters = { name, facultyId };
        try {
            Department dep = departmentService.createDepartment(parameters);
            System.out.println("Создана кафедра: " + dep);
        } catch (Exception e) {
            System.out.println("Ошибка при создании кафедры: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Create new department";
    }
}
