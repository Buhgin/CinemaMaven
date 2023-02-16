package ru.username.model;

import java.util.List;

public interface CRUDModel<T> {
    void create(T objectClass);
  List<T> select();
    T slectID(Long id);

    void update(T objectClass);
    void delete(T objectClass);
}
