package org.example.repository;

import org.example.entities.Group;
import org.example.entities.Student;

import java.util.List;

public interface StudentRepository extends Repository<Student> {
    List<Student> getStudentsByLastName(String lastName);
    List<Student> getStudentsByGroup(int groupId);
    List<Student> getStudentsByAdmissionYear(int year);
    void deleteStudentById(int id);
}
