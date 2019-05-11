package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dao.jdbc.JdbcUserDaoImpl;
import ru.kpfu.itis.entity.User;

import java.sql.Connection;
import java.util.List;

public class UserService {

    private UserDao udao;

    public UserService() {
        Connection connection = ConnectionManager.getConnection();
        udao = new JdbcUserDaoImpl(connection);
    }

    public User get(String login) {
        return udao.get(login);
    }

    public List<User> getAll() {
        return udao.getAll();
    }

    public void create(User item) {
        udao.create(item);
    }

    public void update(String login, User newItem) {
        udao.update(login,newItem);
    }

    public List<User> getSubscribed() {
        return udao.getSubscribed();
    }

    public void delete(String login) {
        udao.delete(login);
    }
}
