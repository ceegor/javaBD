package org.example.cli;

import org.example.cli.department.CreateDepartment;
import org.example.cli.department.DeleteDepartmentById;
import org.example.cli.department.GetAllDepartments;
import org.example.cli.department.GetDepartmentById;
import org.example.cli.faculty.CreateFaculty;
import org.example.cli.faculty.DeleteFacultyById;
import org.example.cli.faculty.GetAllFaculties;
import org.example.cli.faculty.GetFacultyById;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final Scanner scn = new Scanner(System.in);

    private static final Command[] commands = new Command[]{
            new GetAllDepartments(),
            new GetDepartmentById(),
            new CreateDepartment(),
            new DeleteDepartmentById(),
            new GetAllFaculties(),
            new GetFacultyById(),
            new CreateFaculty(),
            new DeleteFacultyById()
    };

    public static void run() {
        while (true) {
            System.out.println("\n--- Меню ---");
            for (int i = 1; i <= commands.length; i++) {
                System.out.println(i + ". " + commands[i - 1].getCommandName());
            }
            System.out.println("-1. Выход");

            int inputCommand;
            try {
                System.out.print("Введите номер команды: ");
                inputCommand = scn.nextInt();
                scn.nextLine();
            } catch (InputMismatchException ime) {
                System.out.println("Некорректный ввод. Введите число!");
                scn.nextLine();
                continue;
            }

            if (inputCommand == -1) {
                System.out.println("Выход из программы...");
                return;
            }

            if (inputCommand < 1 || inputCommand > commands.length) {
                System.out.println("Неверный номер команды, попробуйте снова.");
                continue;
            }

            try {
                commands[inputCommand - 1].execute();
            } catch (Exception e) {
                System.out.println("Ошибка при выполнении команды: " + e.getMessage());
            }
        }
    }
}
