package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.User;

import java.util.List;

public interface UserDao {
    public User get(String login);

    public List<User> getAll();

    public void create(User item);

    public void update(String login, User newItem);

    public void delete(String login);
}
