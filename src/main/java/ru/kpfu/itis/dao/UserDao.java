package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.User;

import java.util.List;

public interface UserDao {
    public User get(String login);

    public List<User> getAll();

    public void create(User item);

    public void update(int id, User newItem);

    public void delete(int id);
}
