package org.example.service;

import org.example.entities.Department;
import org.example.entities.Faculty;

import java.util.List;

public interface DepartmentService extends Service<Department> {
    Department getDepartmentByName(String name);
    List<Department> getDepartmentsByFaculty(Faculty faculty);
    Department createDepartment(String[] parameters);
}
