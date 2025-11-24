package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
@Setter
@NoArgsConstructor
public class Group {

    private Integer id;
    private String name;
    private short year;
    private int departmentId;

    public Group(String name, short year, int departmentId) {
        setName(name);
        setYear(year);
        setDepartmentId(departmentId);
    }

    public Group(Integer id, String name, short year, int departmentId) {
        this(name, year, departmentId);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", department=" + departmentId +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group g = (Group) o;
        return id == g.id && departmentId == g.departmentId && name.equals(g.name) && year == g.year;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + Integer.hashCode(departmentId);
        result = 31 * result + Short.hashCode(year);
        return result;
    }
}
