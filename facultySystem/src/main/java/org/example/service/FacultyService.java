package org.example.service;

import org.example.entities.Faculty;

import java.util.List;

public interface FacultyService extends Service<Faculty> {
    Faculty getFacultyByName(String name);
    Faculty getFacultyByDean(String dean);
}
