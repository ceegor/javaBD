package org.example.service;

import org.example.entities.Department;
import org.example.entities.Faculty;
import org.example.repository.DepartmentRepository;
import org.example.repository.DepartmentRepositoryImpl;
import org.example.repository.FacultyRepositoryImpl;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private static DepartmentServiceImpl instance;
    private final DepartmentRepository departmentRepository;

    private DepartmentServiceImpl() {
        this.departmentRepository = DepartmentRepositoryImpl.getInstance();
    }

    public static DepartmentService getInstance() {
        if (instance == null) {
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }

    private Department setInfoForNewDepartment(String[] parameters) {
        String name = parameters[0];
        int facultyId = Integer.parseInt(parameters[1]);
        Faculty faculty = FacultyRepositoryImpl.getInstance().getById(facultyId);
        return new Department(name, faculty);
    }

    public Department createDepartment(String[] parameters) {
        Department department = setInfoForNewDepartment(parameters);
        departmentRepository.add(department);
        return department;
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.getDepartmentByName(name);
    }

    @Override
    public List<Department> getDepartmentsByFaculty(Faculty faculty) {
        return departmentRepository.getDepartmentsByFaculty(faculty);
    }

    @Override
    public void add(Department department) {
        departmentRepository.add(department);
    }

    @Override
    public void delete(int id) {
        Department dep = departmentRepository.getById(id);
        if (dep != null) {
            departmentRepository.getAll().remove(dep);
        }
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
}
