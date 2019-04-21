package ru.kpfu.itis;

import ru.kpfu.itis.dao.EventDao;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dao.jdbc.JdbcEventDaoImpl;
import ru.kpfu.itis.dao.jdbc.JdbcUserDaoImpl;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.EventModel;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.ConnectionManager;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class bdmain {
    public static void main(String[] args) {
        UserService us = new UserService();
        EventService es = new EventService();

        User user = new User("Anton", "Kemerov", "Maksimovich", 10, "sdsds", "123", Role.REGULAR);
        Event event = new Event("quiz", "really hard quiz", 10, 30, "querty", true, "kfu", "10:00", "12:00", "20.04.19", "20.04.19");

        us.create(user);
        es.create(event);
        List<User> users = new ArrayList<>();
        users.add(user);

        event.setParticipants(users);

        es.update(event.getId(), event);

        Event e = es.get(event.getId());

        ConnectionManager.closeConnection();
    }

}
