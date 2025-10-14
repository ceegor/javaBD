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
    private Faculty faculty;

    public Department(String name, Faculty faculty) {
        this.id = DEPARTMENT_INSTANCE_COUNTER.incrementAndGet();
        this.faculty = faculty;
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
