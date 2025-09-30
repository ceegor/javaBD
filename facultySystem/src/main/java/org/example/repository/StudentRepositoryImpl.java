package org.example.repository;

import org.example.entities.Group;
import org.example.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StudentRepositoryImpl implements StudentRepository {
    private static StudentRepositoryImpl instance;
    private final Map<Integer, Student> storage = new ConcurrentHashMap<>();

    private StudentRepositoryImpl() {}

    public static StudentRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new StudentRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Student> getStudentsByAdmissionYear(int year) {
        List<Student> result = new ArrayList<>();
        for (Student s : storage.values()) {
            if (s.getAdmissionYear() == year) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public List<Student> getStudentsByGroup(Group group) {
        List<Student> result = new ArrayList<>();
        for (Student s : storage.values()) {
            if (s.getGroup() != null && s.getGroup().equals(group)) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        List<Student> result = new ArrayList<>();
        for (Student s : storage.values()) {
            if (s.getLastName().equalsIgnoreCase(lastName)) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public void add(Student student) {
        storage.put(student.getId(), student);
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Student getById(int id) {
        return storage.get(id);
    }

    @Override
    public void removeAll() {
        storage.clear();
    }

    @Override
    public void update(int id, Student student) {
        storage.put(id, student);
    }
}
