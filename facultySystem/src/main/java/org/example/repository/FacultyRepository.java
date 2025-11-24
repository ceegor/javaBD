package org.example.repository;

import org.example.entities.Faculty;

import java.util.List;

public interface FacultyRepository extends Repository<Faculty> {
    Faculty getFacultyByDean(String dean);
    Faculty getFacultyByName(String name);
    void deleteFacultyById(int id);
    List<Faculty> findPaged(String nameLike, int limit, int offset, String sortBy, boolean asc);
    int count(String nameLike);
}
