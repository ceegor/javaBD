package org.example.repository;

import org.example.entities.Department;
import org.example.entities.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupRepositoryImpl implements GroupRepository {
    private static GroupRepositoryImpl instance;
    private final Map<Integer, Group> storage = new ConcurrentHashMap<>();

    private GroupRepositoryImpl() {}

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Group> getGroupsByDepartment(Department department) {
        List<Group> result = new ArrayList<>();
        for (Group g : storage.values()) {
            if (g.getDepartment() != null && g.getDepartment().equals(department)) {
                result.add(g);
            }
        }
        return result;
    }

    @Override
    public Group getGroupByName(String name) {
        return storage.values().stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Group> getGroupsByYear(short year) {
        List<Group> result = new ArrayList<>();
        for (Group g : storage.values()) {
            if (g.getYear() == year) {
                result.add(g);
            }
        }
        return result;
    }

    @Override
    public void add(Group group) {
        storage.put(group.getId(), group);
    }

    @Override
    public List<Group> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Group getById(int id) {
        return storage.get(id);
    }

    @Override
    public void removeAll() {
        storage.clear();
    }

    @Override
    public void update(int id, Group group) {
        storage.put(id, group);
    }
}
