package org.example.service;

import org.example.entities.Faculty;
import org.example.entities.Student;
import org.example.repository.FacultyRepository;
import org.example.repository.FacultyRepositoryImpl;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private static FacultyServiceImpl instance;
    private final FacultyRepository facultyRepository;

    private FacultyServiceImpl() {
        this.facultyRepository = FacultyRepositoryImpl.getInstance();
    }

    public static FacultyService getInstance() {
        if (instance == null) {
            instance = new FacultyServiceImpl();
        }
        return instance;
    }

    private Faculty setInfoForNewFaculty(String[] parameters) {
        String name = parameters[0];
        String dean = parameters[1];
        return new Faculty(name, dean);
    }

    @Override
    public Faculty createFaculty(String[] parameters) {
        Faculty faculty = setInfoForNewFaculty(parameters);
        facultyRepository.add(faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyByName(String name) {
        return facultyRepository.getFacultyByName(name);
    }

    @Override
    public Faculty getFacultyByDean(String dean) {
        return facultyRepository.getFacultyByDean(dean);
    }

    @Override
    public Faculty getById(int id) { return facultyRepository.getById(id); }

    @Override
    public List<Faculty> getAll() { return facultyRepository.getAll(); }

    @Override
    public void add(Faculty faculty) {
        facultyRepository.add(faculty);
    }
    @Override public void update(int id, Faculty faculty) { facultyRepository.update(id, faculty); }

    @Override public void delete(int id) {
        facultyRepository.deleteFacultyById(id);
    }

    @Override public void removeAll() { facultyRepository.removeAll(); }
}
