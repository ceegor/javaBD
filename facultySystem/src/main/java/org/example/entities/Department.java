package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
public class Department {

    private Integer id;
    private String name;
    private int facultyId;

    public Department(String name, int facultyId) {
        setName(name);
        setFacultyId(facultyId);
    }

    public Department(Integer id, String name, int facultyId) {
        this(name, facultyId);
        this.id = id;
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
