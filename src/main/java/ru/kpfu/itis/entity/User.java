package ru.kpfu.itis.entity;

import java.util.List;

public class User {
    private String name;
    private String lastname;
    private String patronymic;
    private int id;
    private int points = 0;
    private String login;
    private String password;
    private List<Event> accomplishedEvents;
    private Role role;

    public User() {
    }

    public void addPoints(int amount) {
        this.points += amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getAccomplishedEvents() {
        return accomplishedEvents;
    }

    public void setAccomplishedEvents(List<Event> accomplishedEvents) {
        this.accomplishedEvents = accomplishedEvents;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
