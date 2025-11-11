package org.example.service;

import org.example.entities.Department;
import org.example.entities.Group;
import org.example.entities.Student;
import org.example.repository.GroupRepository;
import org.example.repository.GroupRepositoryImpl;
import org.example.repository.StudentRepository;
import org.example.repository.StudentRepositoryImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private static StudentServiceImpl instance;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    private StudentServiceImpl() {
        this.studentRepository = StudentRepositoryImpl.getInstance();
        this.groupRepository = GroupRepositoryImpl.getInstance();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Student> getStudentsByDepartment(int departmentId) {
        Set<Integer> groupIds = groupRepository.getGroupsByDepartment(departmentId)
                .stream()
                .map(Group::getId)
                .collect(Collectors.toSet());

        if (groupIds.isEmpty()) return List.of();

        return studentRepository.getAll().stream()
                .filter(s -> groupIds.contains(s.getGroupId()))
                .toList();
    }

    @Override
    public List<Student> getStudentsByGroup(int groupId) {
        return studentRepository.getAll().stream()
                .filter(s -> s.getGroupId() >= 0 &&
                        s.getGroupId() == groupId)
                .toList();
    }

    @Override
    public Student findStudentsByStudentCode(long studentCode) {
        return studentRepository.getAll().stream()
                .filter(s -> s.getStudentCode() == studentCode)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(int id) {
        studentRepository.deleteStudentById(id);
    }

    @Override
    public List<Student> getStudentsOlderThan(int age) {
        return studentRepository.getAll().stream()
                .filter(s -> s.getDateOfBirth() != null &&
                        Period.between(s.getDateOfBirth(), LocalDate.now()).getYears() > age)
                .toList();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return studentRepository.getAll().stream()
                .noneMatch(s -> s.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.getAll();
    }

    @Override
    public Student getById(int id) {
        return studentRepository.getById(id);
    }

    @Override
    public void removeAll() {
        studentRepository.removeAll();
    }

    @Override
    public void update(int id, Student student) {
        studentRepository.update(id, student);
    }

    @Override
    public void add(Student student) {
        studentRepository.add(student);
    }
}
