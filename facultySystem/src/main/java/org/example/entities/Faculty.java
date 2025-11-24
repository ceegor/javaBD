package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;


@Getter
@Setter
@NoArgsConstructor
public class Faculty {

    private Integer id;
    private String name;
    private String dean;

    public Faculty(String name, String dean) {
        setName(name);
        setDean(dean);
    }

    public Faculty(Integer id, String name, String dean) {
        this(name, dean);
        this.id = id;
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
