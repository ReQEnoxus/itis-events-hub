package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.User;

import java.util.List;

public interface UserDao {
    User get(String login);

    List<User> getAll();

    void create(User item);

    void update(String login, User newItem);

    void delete(String login);
}
