package org.example.service;

import org.example.entities.Department;
import org.example.entities.Student;
import org.example.repository.StudentRepository;
import org.example.repository.StudentRepositoryImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private static StudentServiceImpl instance;
    private final StudentRepository studentRepository;

    private StudentServiceImpl() {
        this.studentRepository = StudentRepositoryImpl.getInstance();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentServiceImpl();
        }
        return instance;
    }

    private Student setInfoForNewStudent(String[] parameters) {
        return null;
    }

    @Override
    public Student createStudent(String[] parameters) {
        return null;
    }

    @Override
    public List<Student> getStudentsByDepartment(Department department) {
        return studentRepository.getAll().stream()
                .filter(s -> s.getGroup() != null &&
                        s.getGroup().getDepartment() != null &&
                        s.getGroup().getDepartment().equals(department))
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
