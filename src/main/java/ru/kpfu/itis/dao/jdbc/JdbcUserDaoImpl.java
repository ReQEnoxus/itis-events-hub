package ru.kpfu.itis.dao.jdbc;

import ru.kpfu.itis.dao.RowMapper;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoImpl implements UserDao {

    private Connection connection;

    public JdbcUserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<User> userRowMapper = rs -> new User(
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("patronymic"),
            rs.getString("email"),
            rs.getString("userinfo"),
            rs.getString("unigroup"),
            rs.getBoolean("subscribed"),
            rs.getInt("points"),
            rs.getString("login"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role"))
    );

    private List<Integer> getAccomplishedEventList(String login) {
        String SQL = "SELECT * FROM user_event WHERE userlogin= ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> eventlist = new ArrayList<>();
                while (rs.next()) {
                    eventlist.add(rs.getInt("eventid"));
                }
                return eventlist;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Integer> getCurrentEventList(String login) {
        String SQL = "SELECT * FROM event_user WHERE userlogin= ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> eventlist = new ArrayList<>();
                while (rs.next()) {
                    eventlist.add(rs.getInt("eventid"));
                }
                return eventlist;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User get(String login) {
        String SQL = "SELECT * FROM vol_user WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                User u = null;
                if (rs.next()) {
                    u = userRowMapper.mapRow(rs);
                    u.setAccomplishedEvents(getAccomplishedEventList(login));
                    u.setCurrentEvents(getCurrentEventList(login));
                }
                return u;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getAll() {
        String SQL = "SELECT * FROM vol_user";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User u = userRowMapper.mapRow(rs);
                u.setAccomplishedEvents(getAccomplishedEventList(u.getLogin()));
                u.setCurrentEvents(getCurrentEventList(u.getLogin()));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getSubscribed() {
        String SQL = "SELECT * FROM vol_user WHERE subscribed = true";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User u = userRowMapper.mapRow(rs);
                u.setAccomplishedEvents(getAccomplishedEventList(u.getLogin()));
                u.setCurrentEvents(getCurrentEventList(u.getLogin()));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void create(User item) {
        String login = item.getLogin();
        String SQL = "INSERT INTO vol_user(first_name, last_name, patronymic, email, userinfo, unigroup, subscribed, points, login, password,  role) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getLastname());
            stmt.setString(3, item.getPatronymic());
            stmt.setString(4, item.getEmail());
            stmt.setString(5, item.getDescription());
            stmt.setString(6, item.getGroup());
            stmt.setBoolean(7, item.isSubscribed());
            stmt.setInt(8, item.getPoints());
            stmt.setString(9, login);
            stmt.setString(10, item.getPassword());
            stmt.setString(11, item.getRole().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        List<Integer> list = item.getAccomplishedEvents();
        for (int id : list) {
            addAccomplishedEvent(login, id);
        }
        list = item.getCurrentEvents();
        for (int id : list) {
            addCurrentEvent(login, id);
        }
    }

    @Override
    public void update(String login, User newItem) {
        String SQL = "UPDATE vol_user SET " +
                "first_name = ?, last_name = ?, patronymic = ?, email = ?, userinfo = ?, unigroup = ?, subscribed = ?, points = ?, login = ?, password = ?,  role = ? " +
                "WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, newItem.getName());
            stmt.setString(2, newItem.getLastname());
            stmt.setString(3, newItem.getPatronymic());
            stmt.setString(4, newItem.getEmail());
            stmt.setString(5, newItem.getDescription());
            stmt.setString(6, newItem.getGroup());
            stmt.setBoolean(7, newItem.isSubscribed());
            stmt.setInt(8, newItem.getPoints());
            stmt.setString(9, newItem.getLogin());
            stmt.setString(10, newItem.getPassword());
            stmt.setString(11, newItem.getRole().toString());
            stmt.setString(12, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        List<Integer> list = newItem.getAccomplishedEvents();
        removeAllAccomplishedEvents(login);
        for (int id : list) {
            addAccomplishedEvent(login, id);
        }
        list = newItem.getCurrentEvents();
        removeAllCurrentEvents(login);
        for (int id : list) {
            addCurrentEvent(login, id);
        }
    }

    @Override
    public void delete(String login) {
        String SQL = "DELETE FROM user_event WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM event_user WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM vol_event WHERE hostlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM vol_user WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void addAccomplishedEvent(String login, int id) {
        String SQL = "INSERT INTO user_event(userlogin, eventid) " +
                "VALUES (?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void addCurrentEvent(String login, int id) {
        String SQL = "INSERT INTO event_user(eventid, userlogin) " +
                "VALUES (?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            stmt.setString(2, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void removeAllAccomplishedEvents(String login) {
        String SQL = "DELETE FROM user_event " +
                "WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void removeAllCurrentEvents(String login) {
        String SQL = "DELETE FROM event_user " +
                "WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
