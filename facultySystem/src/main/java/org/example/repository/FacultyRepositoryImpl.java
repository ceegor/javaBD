package org.example.repository;

import org.example.entities.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FacultyRepositoryImpl implements FacultyRepository {
    private static FacultyRepositoryImpl instance;
    private final Map<Integer, Faculty> storage = new ConcurrentHashMap<>();

    private FacultyRepositoryImpl() {}

    public static FacultyRepository getInstance() {
        if (instance == null) {
            instance = new FacultyRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(Faculty faculty) {
        storage.put(faculty.getId(), faculty);
    }

    @Override
    public Faculty getFacultyByDean(String dean) {
        return storage.values().stream()
                .filter(f -> f.getDean().equalsIgnoreCase(dean))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Faculty getFacultyByName(String name) {
        return storage.values().stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Faculty> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Faculty getById(int id) {
        return storage.get(id);
    }

    @Override
    public void removeAll() {
        storage.clear();
    }

    @Override
    public void update(int id, Faculty faculty) {
        storage.put(id, faculty);
    }
}
