package org.example.service;

import org.example.entities.Department;
import org.example.entities.Student;

import java.util.List;

public interface StudentService extends Service<Student> {
    List<Student> getStudentsOlderThan(int age);
    List<Student> getStudentsByDepartment(Department department);
    boolean isEmailUnique(String email);
    Student findStudentsByStudentCode(long studentCode);
}
