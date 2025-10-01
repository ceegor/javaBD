package org.example.repository;

import org.example.entities.Department;
import org.example.entities.Faculty;

import java.util.List;

public interface DepartmentRepository extends Repository<Department> {
    Department getDepartmentByName(String name);
    List<Department> getDepartmentsByFaculty(Faculty faculty);
    void deleteDepartmentById(int id);
}
