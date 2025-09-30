package org.example.entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Group {
    private static final AtomicInteger GROUP_INSTANCE_COUNTER = new AtomicInteger(0);

    private final int id;
    private final String name;
    private short year;
    private Department department;

    public Group(String name, short year, Department department) {
        this.id = GROUP_INSTANCE_COUNTER.incrementAndGet();
        this.department = department;
        this.name = name;
        this.year = year;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", department=" + department +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
