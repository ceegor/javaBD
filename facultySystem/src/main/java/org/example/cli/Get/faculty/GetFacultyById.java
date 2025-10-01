package org.example.cli.Get.faculty;

import org.example.cli.Command;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.util.Scanner;

public class GetFacultyById implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final FacultyService facultyService;

    public GetFacultyById() {
        this.facultyService = FacultyServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите ID факультета: ");
        int id = scn.nextInt();
        scn.nextLine();

        Faculty faculty = facultyService.getById(id);
        if (faculty != null) {
            System.out.println("Найден факультет: " + faculty);
        } else {
            System.out.println("Факультет с ID " + id + " не найден.");
        }
    }

    @Override
    public String getCommandName() {
        return "Get faculty by id";
    }
}
