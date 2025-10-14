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
        int id;
        try {
            id = Integer.parseInt(scn.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ID. Введите целое число.");
            return;
        }

        Department department = departmentService.getById(id);
        if (department == null) {
            System.out.println("Кафедра с ID " + id + " не найдена.");
            return;
        }

        System.out.println("Текущее состояние: " + department);

        System.out.print("Введите новое название (Enter — оставить текущее): ");
        String newName = scn.nextLine().trim();
        if (!newName.isEmpty()) {
            department.setName(newName);
        } else {
            System.out.println("Название не изменено.");
        }

        System.out.print("Введите новый ID факультета (Enter — оставить текущий): ");
        String facultyInput = scn.nextLine().trim();
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
        } else {
            System.out.println("Факультет не изменён.");
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
