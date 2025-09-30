package org.example.service;

import org.example.entities.Department;
import org.example.entities.Group;

import java.util.List;

public interface GroupService extends Service<Group> {
    Group getGroupByName(String name);
    List<Group> getGroupsByYear(short year);
    List<Group> getGroupsByDepartment(Department department);
}
