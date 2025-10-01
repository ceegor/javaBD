package org.example.cli.faculty;

import org.example.cli.Command;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.util.Scanner;

public class DeleteFacultyById implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final FacultyService facultyService;

    public DeleteFacultyById() {
        this.facultyService = FacultyServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите ID факультета для удаления: ");
        int id = scn.nextInt();
        scn.nextLine();

        Faculty faculty = facultyService.getById(id);
        if (faculty == null) {
            System.out.println("Факультет с таким ID не найден.");
            return;
        }

        facultyService.delete(id);
        System.out.println("Факультет удалён: " + faculty);
    }

    @Override
    public String getCommandName() {
        return "Delete faculty by id";
    }
}
