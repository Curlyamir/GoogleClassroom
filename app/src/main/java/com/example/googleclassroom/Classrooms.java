package com.example.googleclassroom;

public class Classrooms
{
    private String name;
    private int size,index;

    public Classrooms(String name, int size,int index) {
        this.name = name;
        this.size = size;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getIndex() {
        return index;
    }
}
