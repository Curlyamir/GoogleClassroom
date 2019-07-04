package com.example.googleclassroom;

import java.io.Serializable;
import java.util.ArrayList;

public class Class implements Serializable {
    private static final long serialVersionUID = 93074495851L;
    String id;
    int index;
    String name;
    String description;
    String[] notification;
    String roomNumber;
    ArrayList<User> teachers;
    ArrayList<User> students;

    public Class(String name, String description, String roomNumber , String id,int index) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.teachers =new ArrayList<User>();
        this.students = new ArrayList<User>();
        this.index = index;
    }

    public Class(String name, String description, String roomNumber) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
    }

    public Class(String name, String description, String roomNumber, ArrayList<User> teachers, ArrayList<User> stuents) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.teachers = teachers;
        this.students = stuents;
    }

    public void setTeachers(ArrayList<User> teachers) {
        this.teachers = teachers;
    }

    public void setNotification(String[] notification) {
        this.notification = notification;
    }

    public String[] getNotification() {
        return notification;
    }

    static ArrayList<Class> classes = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getStudentsSize() {
        return this.students.size();
    }
    public boolean findTeacher(User myuser)
    {
        return this.teachers.contains(myuser);
    }
}
