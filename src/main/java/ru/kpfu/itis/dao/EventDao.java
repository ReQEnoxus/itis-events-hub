package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.EventModel;

import java.util.List;

public interface EventDao {
    EventModel get(int id);

    List<EventModel> getAll();

    List<EventModel> getActive();

    void create(EventModel item);

    void update(int id, EventModel newItem);

    void delete(int id);

}
