package org.example.repository;

import java.util.List;

public interface Repository<T> {
    void add(T object);
    void update(int id, T object);
    void removeAll();
    T getById(int id);
    List<T> getAll();
}
