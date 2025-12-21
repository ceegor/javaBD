package org.example.repository;

import org.example.entities.Department;
import org.example.entities.Faculty;

import java.util.List;

public interface DepartmentRepository extends Repository<Department> {
    Department getDepartmentByName(String name);
    List<Department> getDepartmentsByFaculty(int facultyId);
    void deleteDepartmentById(int id);
    List<Department> findPaged(String nameLike, Integer facultyId, int limit, int offset,
                               String sortBy, boolean asc);
    int count(String nameLike, Integer facultyId);
    int countByFacultyId(int facultyId);
}
