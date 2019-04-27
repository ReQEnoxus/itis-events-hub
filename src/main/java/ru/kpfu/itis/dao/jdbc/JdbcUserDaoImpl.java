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
    public JdbcUserDaoImpl(Connection connection){
        this.connection = connection;
    }

    private RowMapper<User> userRowMapper = rs -> new User(
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("patronymic"),
            rs.getInt("points"),
            rs.getString("login"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role"))
    );

    private List<Integer> getEventList (String login){
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
        } catch(SQLException e) {
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
                    u.setAccomplishedEvents(getEventList(login));
                }
                return u;
            }
        } catch(SQLException e) {
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
                u.setAccomplishedEvents(getEventList(u.getLogin()));
                users.add(u);
            }
            return users;
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }



    @Override
    public void create(User item) {
        String login = item.getLogin();
        String SQL = "INSERT INTO vol_user(first_name, last_name, patronymic, points, login, password,  role) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getLastname());
            stmt.setString(3, item.getPatronymic());
            stmt.setInt(4, item.getPoints());
            stmt.setString(5, login);
            stmt.setString(6, item.getPassword());
            stmt.setString(7, item.getRole().toString());
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
        List<Integer> list = item.getAccomplishedEvents();
        for (int id: list) {
            addEvent(login,id);
        }
    }

    @Override
    public void update(String login, User newItem) {
            String SQL = "UPDATE vol_user SET " +
                    "first_name = ?, last_name = ?, patronymic = ?, points = ?, login = ?, password = ?,  role = ? " +
                    "WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
                stmt.setString(1, newItem.getName());
                stmt.setString(2, newItem.getLastname());
                stmt.setString(3, newItem.getPatronymic());
                stmt.setInt(4, newItem.getPoints());
                stmt.setString(5, newItem.getLogin());
                stmt.setString(6, newItem.getPassword());
                stmt.setString(7, newItem.getRole().toString());
                stmt.setString(8, login);
                stmt.executeUpdate();
            } catch(SQLException e) {
                throw new IllegalStateException(e);
            }
            List<Integer> list = newItem.getAccomplishedEvents();
            removeAllEvents(login);
            for (int id: list) {
                addEvent(login,id);
            }
    }

    @Override
    public void delete(String login) {
        String SQL = "DELETE FROM user_event WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM event_user WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM vol_event WHERE hostlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM vol_user WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void addEvent(String login, int id) {
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

    private void removeAllEvents (String login) {
        String SQL = "DELETE FROM user_event " +
                "WHERE userlogin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
