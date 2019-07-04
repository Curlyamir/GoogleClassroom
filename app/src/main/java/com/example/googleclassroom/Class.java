package com.example.googleclassroom;

import java.io.Serializable;
import java.util.ArrayList;

public class Class implements Serializable {
    private static final long serialVersionUID = 93074495851L;

    int id;
    String name;
    String description;
    String[] notification;
    int roomNumber;
    ArrayList<User> teachers;
    ArrayList<User> students;

    public Class(int id, String name, String description, int roomNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
    }

    public Class(String name, String description, int roomNumber) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
    }

    public Class(String name, String description, int roomNumber, ArrayList<User> teachers, ArrayList<User> stuents) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.teachers = teachers;
        this.students = stuents;
    }

    public void setNotification(String[] notification) {
        this.notification = notification;
    }

    public String[] getNotification() {
        return notification;
    }

}

