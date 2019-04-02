package ru.kpfu.itis.dao;

import java.util.List;

public interface Dao<T> {
    public T get(int id);

    public List<T> getAll();

    public void add(T item);

    public void update(int id, T newItem);

    public void delete(int id);
}
