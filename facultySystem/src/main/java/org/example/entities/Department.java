package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Department {
    private static final AtomicInteger DEPARTMENT_INSTANCE_COUNTER = new AtomicInteger(0);

    private final int id;
    private String name;
    private int facultyId;

    public Department(String name, int facultyId) {
        this.id = DEPARTMENT_INSTANCE_COUNTER.incrementAndGet();
        this.facultyId = facultyId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", faculty=" + facultyId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department d = (Department) o;
        return id == d.id && facultyId == d.facultyId && name.equals(d.name);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + Integer.hashCode(facultyId);
        return result;
    }
}
