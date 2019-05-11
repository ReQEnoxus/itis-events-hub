package ru.kpfu.itis.dao.jdbc;

import ru.kpfu.itis.dao.EventDao;
import ru.kpfu.itis.dao.RowMapper;
import ru.kpfu.itis.entity.EventModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEventDaoImpl implements EventDao {

    private Connection connection;

    public JdbcEventDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<EventModel> eventRowMapper = rs -> new EventModel(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getInt("prize"),
            rs.getInt("capacity"),
            rs.getString("hostlogin"),
            rs.getBoolean("active"),
            rs.getString("place"),
            rs.getString("timestart"),
            rs.getString("timeend"),
            rs.getString("datestart"),
            rs.getString("dateend")
    );

    private List<String> getUserList(int id) {
        String SQL = "SELECT * FROM event_user WHERE eventid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> userlist = new ArrayList<>();
                while (rs.next()) {
                    userlist.add(rs.getString("userlogin"));
                }
                return userlist;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public EventModel get(int id) {
        String SQL = "SELECT * FROM vol_event WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                EventModel e = null;
                if (rs.next()) {
                    e = eventRowMapper.mapRow(rs);
                    e.setParticipants(getUserList(id));
                }
                return e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<EventModel> getAll() {
        String SQL = "SELECT * FROM vol_event";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL);
            List<EventModel> events = new ArrayList<>();
            while (rs.next()) {
                EventModel e = eventRowMapper.mapRow(rs);
                e.setParticipants(getUserList(e.getId()));
                events.add(e);
            }
            return events;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<EventModel> getActive() {
        String SQL = "SELECT * FROM vol_event WHERE active = true";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL);
            List<EventModel> events = new ArrayList<>();
            while (rs.next()) {
                EventModel e = eventRowMapper.mapRow(rs);
                e.setParticipants(getUserList(e.getId()));
                events.add(e);
            }
            return events;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void create(EventModel item) {
        int id = 0;
        String SQL = "INSERT INTO vol_event(name, description, prize, capacity, hostlogin, active,  place, timestart, timeend, datestart, dateend) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setInt(3, item.getPrize());
            stmt.setInt(4, item.getCapacity());
            stmt.setString(5, item.getHost());
            stmt.setBoolean(6, item.isActive());
            stmt.setString(7, item.getPlace());
            stmt.setString(8, item.getTimeStart());
            stmt.setString(9, item.getTimeEnd());
            stmt.setString(10, item.getDateStart());
            stmt.setString(11, item.getDateEnd());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        SQL = "SELECT * FROM vol_event WHERE name = ? ORDER BY id DESC";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, item.getName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                    item.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        for (String login : item.getParticipants()) {
            addUser(id, login);
        }
    }

    @Override
    public void update(int id, EventModel newItem) {
        String SQL = "UPDATE vol_event SET " +
                "name = ?, description = ?, prize = ?, capacity = ?, hostlogin = ?, active = ?,  place = ?, timestart = ?, timeend = ?, datestart = ?, dateend = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, newItem.getName());
            stmt.setString(2, newItem.getDescription());
            stmt.setInt(3, newItem.getPrize());
            stmt.setInt(4, newItem.getCapacity());
            stmt.setString(5, newItem.getHost());
            stmt.setBoolean(6, newItem.isActive());
            stmt.setString(7, newItem.getPlace());
            stmt.setString(8, newItem.getTimeStart());
            stmt.setString(9, newItem.getTimeEnd());
            stmt.setString(10, newItem.getDateStart());
            stmt.setString(11, newItem.getDateEnd());
            stmt.setInt(12, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        List<String> list = newItem.getParticipants();
        removeAllUsers(id);
        for (String login : list) {
            addUser(id, login);
        }
    }

    @Override
    public void delete(int id) {
        String SQL = "DELETE FROM user_event WHERE eventid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM event_user WHERE eventid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        SQL = "DELETE FROM vol_event WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    private void addUser(int id, String login) {
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

    private void removeAllUsers(int id) {
        String SQL = "DELETE FROM event_user " +
                "WHERE eventid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
