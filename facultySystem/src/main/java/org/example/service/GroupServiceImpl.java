package org.example.service;

import org.example.entities.Department;
import org.example.entities.Group;
import org.example.repository.DepartmentRepositoryImpl;
import org.example.repository.GroupRepository;
import org.example.repository.GroupRepositoryImpl;

import java.util.List;

public class GroupServiceImpl implements GroupService {
    private static GroupServiceImpl instance;
    private final GroupRepository groupRepository;

    private GroupServiceImpl() {
        this.groupRepository = GroupRepositoryImpl.getInstance();
    }

    public static GroupServiceImpl getInstance() {
        if (instance == null) {
            instance = new GroupServiceImpl();
        }
        return instance;
    }

    private Group setInfoForNewGroup(String[] parameters) {
        String name = parameters[0];
        short year = Short.parseShort(parameters[1]);
        int depId = Integer.parseInt(parameters[2]);
        Department dep = DepartmentRepositoryImpl.getInstance().getById(depId);
        return new Group(name, year, dep);
    }

    @Override
    public Group createGroup(String[] parameters) {
        Group group = setInfoForNewGroup(parameters);
        groupRepository.add(group);
        return group;
    }

    @Override
    public Group getGroupByName(String name) {
        return groupRepository.getGroupByName(name);
    }

    @Override
    public List<Group> getGroupsByDepartment(Department department) {
        return groupRepository.getGroupsByDepartment(department);
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
}
