package org.example.cli.Get.faculty;

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
        System.out.print("Введите название факультета: ");
        String name = scn.nextLine();

        System.out.print("Введите имя декана: ");
        String dean = scn.nextLine();

        String[] parameters = { name, dean };

        Faculty faculty = facultyService.createFaculty(parameters);
        System.out.println("Создан факультет: " + faculty);
    }


    @Override
    public String getCommandName() {
        return "Create new faculty";
    }
}
