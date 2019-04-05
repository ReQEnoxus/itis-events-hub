package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Event;

import java.util.List;

public interface EventDao {
    public Event get(int id);

    public List<Event> getAll();

    public void create(Event item);

    public void update(int id, Event newItem);

    public void delete(int id);
}
