package org.example.repository;

import org.example.entities.Department;
import org.example.entities.Group;

import java.util.List;

public interface GroupRepository extends Repository<Group> {
    Group getGroupByName(String name);
    List<Group> getGroupsByYear(short year);
    List<Group> getGroupsByDepartment(int departmentId);
    void deleteGroupById(int id);
}
