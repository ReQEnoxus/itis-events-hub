package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Event;

import java.util.List;

public interface EventDao {
    Event get(int id);

    List<Event> getAll();

    List<Event> getActive();

    void create(Event item);

    void update(int id, Event newItem);

    void delete(int id);
}
