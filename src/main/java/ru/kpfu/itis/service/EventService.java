package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.EventDao;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dao.jdbc.JdbcEventDaoImpl;
import ru.kpfu.itis.dao.jdbc.JdbcUserDaoImpl;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.EventModel;
import ru.kpfu.itis.entity.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EventService {
    private UserDao udao;
    private EventDao edao;

    public EventService() {
        Connection connection = ConnectionManager.getConnection();
        udao = new JdbcUserDaoImpl(connection);
        edao = new JdbcEventDaoImpl(connection);
    }

    private Event modelToEvent(EventModel model){
        Event e = new Event(model.getId(),
                model.getName(),
                model.getDescription(),
                model.getPrize(),
                model.getCapacity(),
                model.getHost(),
                model.isActive(),
                model.getPlace(),
                model.getTimeStart(),
                model.getTimeEnd(),
                model.getDateStart(),
                model.getDateEnd());

        List<String> userLogins = model.getParticipants();
        List<User> userList = new ArrayList<>();
        for (String login: userLogins) {
            userList.add(udao.get(login));
        }
        e.setParticipants(userList);
        return e;
    }

    private EventModel eventToModel(Event e){
        EventModel em = new EventModel(e.getName(),
                e.getDescription(),
                e.getPrize(),
                e.getCapacity(),
                e.getHost(),
                e.isActive(),
                e.getPlace(),
                e.getTimeStart(),
                e.getTimeEnd(),
                e.getDateStart(),
                e.getDateEnd());
        List<User> userList = e.getParticipants();
        List<String> loginList = new ArrayList<>();
        for (User u: userList) {
            loginList.add(u.getLogin());
        }
        em.setParticipants(loginList);
        return em;
    }

    private EventModel eventToModelWithId(Event e){
        EventModel em = new EventModel(e.getId(),
                e.getName(),
                e.getDescription(),
                e.getPrize(),
                e.getCapacity(),
                e.getHost(),
                e.isActive(),
                e.getPlace(),
                e.getTimeStart(),
                e.getTimeEnd(),
                e.getDateStart(),
                e.getDateEnd());
        List<User> userList = e.getParticipants();
        List<String> loginList = new ArrayList<>();
        for (User u: userList) {
            loginList.add(u.getLogin());
        }
        em.setParticipants(loginList);
        return em;
    }


    public Event get(int id){
     return modelToEvent(edao.get(id));
    }

    public List<Event> getAll(){
        List<EventModel> emlist = edao.getAll();
        List<Event> elist = new ArrayList<>();
        for (EventModel em: emlist) {
            elist.add(modelToEvent(em));
        }
        return elist;
    }

    public List<Event> getActive(){
        List<EventModel> emlist = edao.getActive();
        List<Event> elist = new ArrayList<>();
        for (EventModel em: emlist) {
            elist.add(modelToEvent(em));
        }
        return elist;
    }

    public void create(Event item){
        EventModel em = eventToModel(item);
        edao.create(em);
        item.setId(em.getId());
    }

    public void update(int id, Event newItem){
        edao.update(id, eventToModelWithId(newItem));
    }

    public void delete(int id){
        edao.delete(id);
    }

}
