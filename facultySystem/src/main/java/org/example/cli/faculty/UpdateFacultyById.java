package org.example.cli.faculty;

import org.example.cli.Command;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

import java.util.Scanner;

public class UpdateFacultyById implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final FacultyService facultyService;

    public UpdateFacultyById() {
        this.facultyService = FacultyServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите ID факультета для изменения: ");
        int id = scn.nextInt();
        scn.nextLine();

        Faculty faculty = facultyService.getById(id);
        if (faculty == null) {
            System.out.println("Факультет с ID " + id + " не найден.");
            return;
        }

        System.out.println("Текущее состояние: " + faculty);

        System.out.print("Введите новое название (Enter — оставить текущее): ");
        String newName = scn.nextLine();
        if (!newName.isEmpty()) {
            faculty.setName(newName);
        }

        System.out.print("Введите новое имя декана (Enter — оставить текущее): ");
        String newDean = scn.nextLine();
        if (!newDean.isEmpty()) {
            faculty.setDean(newDean);
        }

        facultyService.update(id, faculty);

        System.out.println("Факультет обновлён:");
        System.out.println(faculty);
    }

    @Override
    public String getCommandName() {
        return "Update faculty by id";
    }
}
