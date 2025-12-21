package org.example.repository;

import org.example.entities.Group;
import org.example.entities.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StudentRepository extends Repository<Student> {
    List<Student> getStudentsByLastName(String lastName);
    List<Student> getStudentsByGroup(int groupId);
    List<Student> getStudentsByAdmissionYear(int year);
    void deleteStudentById(int id);
    List<Student> findPaged(
            String lastNameLike, Integer groupId, Integer departmentId, Integer facultyId,
            int limit, int offset, String sortBy, boolean asc);

    int count(String lastNameLike, Integer groupId, Integer departmentId, Integer facultyId);
    Student getByStudentCode(String code);
    boolean existsByEmail(String email);
    List<Student> getByDepartment(int departmentId, int limit, int offset);
    int countByDepartment(int departmentId);
    Map<String, Integer> countByFaculty();
    Map<String, Integer> countByDepartment();
    Map<String, Integer> countByGroup();
    Map<Integer, Integer> countByAdmissionYear();
    int countOlderThan(java.time.LocalDate dateBoundary);
    int countYoungerThan(java.time.LocalDate dateBoundary);
    List<Student> findStudentsOlderThan(LocalDate boundary);
    List<Student> findStudentsYoungerThan(LocalDate boundary);
    int countByGroupId(int groupId);

}
