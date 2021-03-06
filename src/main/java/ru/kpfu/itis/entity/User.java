package ru.kpfu.itis.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String lastname;
    private String patronymic;
    private int points = 0;
    private String login;
    private String password;
    private List<Integer> accomplishedEvents;
    private List<Integer> currentEvents;
    private Role role;
    private String description;
    private String email;
    private String group;
    private boolean subscribed;

    public User(){}

    public User(String name, String lastname, String patronymic, String email, String userinfo, String group, boolean subscribed, int points, String login, String password, Role role) {

        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.description = userinfo;
        this.subscribed = subscribed;
        this.patronymic = patronymic;
        this.points = points;
        this.login = login;
        this.password = password;
        this.role = role;
        this.group = group;
        accomplishedEvents = new ArrayList<>();
        currentEvents = new ArrayList<>();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getCurrentEvents() {
        return currentEvents;
    }

    public void setCurrentEvents(List<Integer> currentEvents) {
        this.currentEvents = currentEvents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
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

    public List<Integer> getAccomplishedEvents() {
        return accomplishedEvents;
    }

    public void setAccomplishedEvents(List<Integer> accomplishedEvents) {
        this.accomplishedEvents = accomplishedEvents;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.login.equals(((User) obj).login);
        } else {
            return super.equals(obj);
        }
    }
}
