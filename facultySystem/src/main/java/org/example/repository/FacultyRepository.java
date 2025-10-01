package org.example.repository;

import org.example.entities.Faculty;

public interface FacultyRepository extends Repository<Faculty> {
    Faculty getFacultyByDean(String dean);
    Faculty getFacultyByName(String name);
    void deleteFacultyById(int id);
}
