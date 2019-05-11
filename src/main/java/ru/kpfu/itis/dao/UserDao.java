package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.User;

import javax.sql.DataSource;
import java.util.List;

public interface UserDao {

    User get(String login);

    List<User> getAll();

    List<User> getSubscribed();

    void create(User item);

    void update(String login, User newItem);

    void delete(String login);
}
