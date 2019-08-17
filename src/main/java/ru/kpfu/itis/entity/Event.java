package ru.kpfu.itis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String description;
    @JsonIgnore
    private List<User> participants;
    private int prize;
    private int capacity;
    private String host;
    private int id;
    private boolean active;
    private String place;
    private String timeStart;
    private String timeEnd;
    private String dateStart;
    private String dateEnd;

    public Event(){}

    public Event(int id, String name, String description, int prize, int capacity, String host, boolean active, String place, String timeStart, String timeEnd, String dateStart, String dateEnd) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.capacity = capacity;
        this.host = host;
        this.active = active;
        this.place = place;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.participants = new ArrayList<>();
    }

    public Event(String name, String description, int prize, int capacity, String host, boolean active, String place, String timeStart, String timeEnd, String dateStart, String dateEnd) {
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.capacity = capacity;
        this.host = host;
        this.active = active;
        this.place = place;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.participants = new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
