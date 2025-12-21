package org.example.service;

import org.example.entities.Faculty;
import org.example.entities.Student;
import org.example.repository.DepartmentRepository;
import org.example.repository.DepartmentRepositoryImpl;
import org.example.repository.FacultyRepository;
import org.example.repository.FacultyRepositoryImpl;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private static FacultyServiceImpl instance;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    private FacultyServiceImpl() {
        this.facultyRepository = FacultyRepositoryImpl.getInstance();
        this.departmentRepository = DepartmentRepositoryImpl.getInstance();
    }

    public static FacultyService getInstance() {
        if (instance == null) {
            instance = new FacultyServiceImpl();
        }
        return instance;
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
        int deps = departmentRepository.countByFacultyId(id);
        if (deps > 0) {
            throw new IllegalStateException(
                    "Нельзя удалить факультет, к нему привязано кафедр - " + deps
            );
        }
        facultyRepository.deleteFacultyById(id);
    }

    @Override public void removeAll() { facultyRepository.removeAll(); }

    @Override
    public List<Faculty> findPaged(String nameLike, int limit, int offset, String sortBy, boolean asc) {
        return facultyRepository.findPaged(nameLike, limit, offset, sortBy, asc);
    }

    @Override
    public int count(String nameLike) {
        return facultyRepository.count(nameLike);
    }

    public boolean existsByName(String name) {
        return getFacultyByName(name) != null;
    }
}
