package org.example.entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Department {
    private static final AtomicInteger DEPARTMENT_INSTANCE_COUNTER = new AtomicInteger(0);

    private final int id;
    private String name;
    private Faculty faculty;

    public Department(int id, Faculty faculty, String name) {
        this.id = DEPARTMENT_INSTANCE_COUNTER.incrementAndGet();
        this.faculty = faculty;
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", faculty=" + faculty +
                ", name='" + name + '\'' +
                '}';
    }
}
