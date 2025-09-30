package org.example.service;

import java.util.List;

public interface Service<T> {
    void add(T object);
    T getById(int id);
    List<T> getAll();
    void removeAll();
    void delete(int id);
    void update(int id, T object);
}
