package com.example.googleclassroom;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 9307449585L;
    String username;
    String password;
    byte[] picture;
    ArrayList<Class> classes = new ArrayList<>();
    boolean logedIn = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, byte[] picture) {
        this.username = username;
        this.password = password;
        this.picture = picture;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.username.equals(((User)obj).username);
    }

    static ArrayList<User> users = new ArrayList<>();

    public String getUsername() {
        return username;
    }
}