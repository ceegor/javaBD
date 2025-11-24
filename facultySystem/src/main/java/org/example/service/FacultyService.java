package org.example.service;

import org.example.entities.Faculty;

import java.util.List;

public interface FacultyService extends Service<Faculty> {
    Faculty getFacultyByName(String name);
    Faculty getFacultyByDean(String dean);
    List<Faculty> findPaged(String nameLike, int limit, int offset, String sortBy, boolean asc);
    int count(String nameLike);

}
