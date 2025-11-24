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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<Student> getStudentsByGroup(int groupId) {
        return studentRepository.getStudentsByGroup(groupId);
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

    @Override
    public List<Student> findPaged(String lastNameLike,
                                   Integer groupId,
                                   Integer departmentId,
                                   Integer facultyId,
                                   int limit,
                                   int offset,
                                   String sortBy,
                                   boolean asc) {
        return studentRepository.findPaged(lastNameLike, groupId, departmentId, facultyId,
                limit, offset, sortBy, asc);
    }

    @Override
    public int count(String lastNameLike,
                     Integer groupId,
                     Integer departmentId,
                     Integer facultyId) {
        return studentRepository.count(lastNameLike, groupId, departmentId, facultyId);
    }

    @Override
    public List<Student> getStudentsByDepartment(int departmentId, int limit, int offset) {
        return studentRepository.getByDepartment(departmentId, limit, offset);
    }

    @Override
    public int countStudentsByDepartment(int departmentId) {
        return studentRepository.countByDepartment(departmentId);
    }

    @Override
    public Student getByStudentCode(String studentCode) {
        if (studentCode == null || studentCode.isBlank()) return null;
        return studentRepository.getByStudentCode(studentCode.trim());
    }

    @Override
    public Student getStudentByStudentCode(String studentCode) {
        return getByStudentCode(studentCode);
    }

    @Override
    public boolean isEmailUnique(String email) {
        if (email == null || email.isBlank()) return true;
        return !studentRepository.existsByEmail(email.trim());
    }

}
