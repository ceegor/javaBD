package org.example;

import org.example.entities.Faculty;
import org.example.entities.Student;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Faculty faculty1 = new Faculty("Легуенко Егор Юрьевич", "Факультет компьютерных наук");
        System.out.println(faculty1.toString());
        Student student1 = new Student.StudentBuilder("Егор", "Легуенко", "M", 2023, 16230319, null).email("привет").build();
        Student student2 = new Student.StudentBuilder("Иван", "Шамаев", "M", 2023, 16230317, null).build();
        System.out.println(student1);
        System.out.println(student2);
    }
}