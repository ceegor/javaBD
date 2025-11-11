package org.example.repository;

import org.example.entities.Department;
import org.example.entities.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static DepartmentRepositoryImpl instance;
    private final Map<Integer, Department> storage = new ConcurrentHashMap<>();

    private DepartmentRepositoryImpl() {}

    public static DepartmentRepository getInstance() {
        if (instance == null) {
            instance = new DepartmentRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Department> getDepartmentsByFaculty(int facultyId) {
        List<Department> result = new ArrayList<>();
        for (Department d : storage.values()) {
            if (d.getFacultyId() >= 0 && d.getFacultyId() == facultyId) {
                result.add(d);
            }
        }
        return result;
    }

    @Override
    public Department getDepartmentByName(String name) {
        return storage.values().stream()
                .filter(d -> d.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(Department department) {
        storage.put(department.getId(), department);
    }

    @Override
    public List<Department> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Department getById(int id) {
        return storage.get(id);
    }

    @Override
    public void removeAll() {
        storage.clear();
    }

    @Override
    public void update(int id, Department department) {
        storage.put(id, department);
    }

    @Override
    public void deleteDepartmentById(int id) {
        storage.remove(id);
    }
}
