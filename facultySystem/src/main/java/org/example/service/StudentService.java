package org.example.service;

import org.example.entities.Department;
import org.example.entities.Student;

import java.util.List;

public interface StudentService extends Service<Student> {
    List<Student> getStudentsOlderThan(int age);
    List<Student> getStudentsByGroup(int groupId);
    Student getStudentByStudentCode(String studentCode);
    List<Student> findPaged(String lastNameLike,
                            Integer groupId,
                            Integer departmentId,
                            Integer facultyId,
                            int limit,
                            int offset,
                            String sortBy,
                            boolean asc);

    int count(String lastNameLike,
              Integer groupId,
              Integer departmentId,
              Integer facultyId);

    List<Student> getStudentsByDepartment(int departmentId, int limit, int offset);
    int countStudentsByDepartment(int departmentId);
    Student getByStudentCode(String studentCode);
    boolean isEmailUnique(String email);

}
