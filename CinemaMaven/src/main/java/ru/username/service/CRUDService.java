package ru.username.service;

import java.util.List;

public interface CRUDService<T> {
    void create(T objectClass);
  List<T> select();
    T slectID(Long id);

    void update(T objectClass);
    void delete(T objectClass);
}
