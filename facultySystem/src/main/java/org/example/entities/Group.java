package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
@Setter
public class Group {
    private static final AtomicInteger GROUP_INSTANCE_COUNTER = new AtomicInteger(0);

    private final int id;
    private final String name;
    private short year;
    private int departmentId;

    public Group(String name, short year, int departmentId) {
        this.id = GROUP_INSTANCE_COUNTER.incrementAndGet();
        this.departmentId = departmentId;
        this.name = name;
        this.year = year;
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
