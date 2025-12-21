package org.example.service;

import org.example.entities.Department;
import org.example.entities.Group;
import org.example.repository.*;

import java.util.List;

public class GroupServiceImpl implements GroupService {
    private static GroupServiceImpl instance;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    private GroupServiceImpl() {
        this.groupRepository = GroupRepositoryImpl.getInstance();
        this.studentRepository = StudentRepositoryImpl.getInstance();
    }

    public static GroupService getInstance() {
        if (instance == null) {
            instance = new GroupServiceImpl();
        }
        return instance;
    }

    @Override
    public Group getGroupByName(String name) {
        return groupRepository.getGroupByName(name);
    }

    @Override
    public List<Group> getGroupsByDepartment(int departmentId) {
        return groupRepository.getGroupsByDepartment(departmentId);
    }

    @Override
    public List<Group> getGroupsByYear(short year) {
        return groupRepository.getGroupsByYear(year);
    }

    @Override
    public void add(Group group) {
        groupRepository.add(group);
    }

    @Override
    public void delete(int id) {
        int stud = studentRepository.countByGroupId(id);
        if (stud > 0) {
            throw new IllegalStateException(
                    "Нельзя удалить группу, в ней студентов - " + stud
            );
        }
        groupRepository.deleteGroupById(id);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.getAll();
    }

    @Override
    public Group getById(int id) {
        return groupRepository.getById(id);
    }

    @Override
    public void removeAll() {
        groupRepository.removeAll();
    }

    @Override
    public void update(int id, Group group) {
        groupRepository.update(id, group);
    }

    @Override
    public List<Group> findPaged(String nameLike, Integer departmentId, Short year,
                                 int limit, int offset, String sortBy, boolean asc) {
        return groupRepository.findPaged(nameLike, departmentId, year, limit, offset, sortBy, asc);
    }

    @Override
    public int count(String nameLike, Integer departmentId, Short year) {
        return groupRepository.count(nameLike, departmentId, year);
    }

}
