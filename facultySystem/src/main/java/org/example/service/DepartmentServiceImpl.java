package org.example.service;

import org.example.entities.Department;
import org.example.entities.Faculty;
import org.example.repository.*;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private static DepartmentServiceImpl instance;
    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;

    private DepartmentServiceImpl() {
        this.departmentRepository = DepartmentRepositoryImpl.getInstance();
        this.groupRepository = GroupRepositoryImpl.getInstance();
    }

    public static DepartmentService getInstance() {
        if (instance == null) {
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.getDepartmentByName(name);
    }

    @Override
    public List<Department> getDepartmentsByFaculty(int facultyId) {
        return departmentRepository.getDepartmentsByFaculty(facultyId);
    }

    @Override
    public void add(Department department) {
        departmentRepository.add(department);
    }

    @Override
    public void delete(int id) {
        int groups = groupRepository.countByDepartmentId(id);
        if (groups > 0) {
            throw new IllegalStateException(
                    "Нельзя удалить кафедру, к ней привязано групп - " + groups
            );
        }
        departmentRepository.deleteDepartmentById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.getAll();
    }

    @Override
    public Department getById(int id) {
        return departmentRepository.getById(id);
    }

    @Override
    public void removeAll() {
        departmentRepository.removeAll();
    }

    @Override
    public void update(int id, Department department) {
        departmentRepository.update(id, department);
    }

    @Override
    public List<Department> findPaged(String nameLike, Integer facultyId,
                                      int limit, int offset, String sortBy, boolean asc) {
        return departmentRepository.findPaged(nameLike, facultyId, limit, offset, sortBy, asc);
    }

    @Override
    public int count(String nameLike, Integer facultyId) {
        return departmentRepository.count(nameLike, facultyId);
    }

}
