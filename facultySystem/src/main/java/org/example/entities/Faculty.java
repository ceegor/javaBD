package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
@Setter
public class Faculty {
    private static final AtomicInteger FACULTY_INSTANCE_COUNTER = new AtomicInteger(0);

    private final int id;
    private String name;
    private String dean;

    public Faculty(String name, String dean) {
        this.id = FACULTY_INSTANCE_COUNTER.incrementAndGet();
        this.dean = dean;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id='" + id + '\'' +
                ", dean=" + dean +
                ", name='" + name + '\'' +
                '}';
    }
}
