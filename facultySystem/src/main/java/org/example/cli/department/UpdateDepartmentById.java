package org.example.cli.department;

import org.example.cli.Command;
import org.example.entities.Department;
import org.example.entities.Faculty;
import org.example.repository.FacultyRepository;
import org.example.repository.FacultyRepositoryImpl;
import org.example.service.DepartmentService;
import org.example.service.DepartmentServiceImpl;

import java.util.Scanner;

public class UpdateDepartmentById implements Command {
    private final Scanner scn = new Scanner(System.in);
    private final DepartmentService departmentService;
    private final FacultyRepository facultyRepository;

    public UpdateDepartmentById() {
        this.departmentService = DepartmentServiceImpl.getInstance();
        this.facultyRepository = FacultyRepositoryImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("Введите ID кафедры для изменения: ");
        int id = scn.nextInt();
        scn.nextLine();

        Department department = departmentService.getById(id);
        if (department == null) {
            System.out.println("Кафедра с ID " + id + " не найдена.");
            return;
        }

        System.out.println("Текущее состояние: " + department);

        System.out.print("Введите новое название (Enter — оставить текущее): ");
        String newName = scn.nextLine();
        if (!newName.isEmpty()) {
            department.setName(newName);
        }

        System.out.print("Введите новый ID факультета (Enter — оставить текущий): ");
        String facultyInput = scn.nextLine();
        if (!facultyInput.isEmpty()) {
            try {
                int facultyId = Integer.parseInt(facultyInput);
                Faculty newFaculty = facultyRepository.getById(facultyId);
                if (newFaculty != null) {
                    department.setFaculty(newFaculty);
                } else {
                    System.out.println("Факультет с таким ID не найден. Старое значение сохранено.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введено не число. Старое значение сохранено.");
            }
        }

        departmentService.update(id, department);

        System.out.println("Кафедра обновлена:");
        System.out.println(department);
    }

    @Override
    public String getCommandName() {
        return "Update department by id";
    }

}
