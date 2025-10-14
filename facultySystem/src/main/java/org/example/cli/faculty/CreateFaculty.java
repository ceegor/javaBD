package org.example.cli.faculty;

import org.example.cli.Command;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.util.Scanner;

public class CreateFaculty implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final FacultyService facultyService;

    public CreateFaculty() {
        this.facultyService = FacultyServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        String name;
        while (true) {
            System.out.print("Введите название факультета: ");
            name = scn.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Название не может быть пустым. Попробуйте снова.");
            } else {
                break;
            }
        }

        String dean;
        while (true) {
            System.out.print("Введите имя декана: ");
            dean = scn.nextLine().trim();
            if (dean.isEmpty()) {
                System.out.println("Имя декана не может быть пустым. Попробуйте снова.");
            } else {
                break;
            }
        }

        String[] parameters = { name, dean };

        Faculty faculty = facultyService.createFaculty(parameters);
        System.out.println("Создан факультет: " + faculty);
    }


    @Override
    public String getCommandName() {
        return "Create new faculty";
    }
}
