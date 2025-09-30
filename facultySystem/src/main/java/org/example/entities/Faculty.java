package org.example.entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Faculty {
    private static final AtomicInteger FACULTY_INSTANCE_COUNTER = new AtomicInteger(0);

    private final int id;
    private String name;
    private String dean;

    public Faculty(String dean, String name) {
        this.id = FACULTY_INSTANCE_COUNTER.incrementAndGet();
        this.dean = dean;
        this.name = name;
    }

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
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
        return "Faculty{" +
                "id='" + id + '\'' +
                ", dean=" + dean +
                ", name='" + name + '\'' +
                '}';
    }
}
