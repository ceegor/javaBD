package org.example.cli.faculty;

import org.example.cli.Command;
import org.example.entities.Faculty;
import org.example.service.FacultyService;
import org.example.service.FacultyServiceImpl;

public class GetAllFaculties implements Command {
    private final FacultyService facultyService;

    public GetAllFaculties() {
        this.facultyService = FacultyServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("Список всех факультетов:");
        for (Faculty f : facultyService.getAll()) {
            System.out.println(f);
        }
    }

    @Override
    public String getCommandName() {
        return "Get all faculties";
    }
}
