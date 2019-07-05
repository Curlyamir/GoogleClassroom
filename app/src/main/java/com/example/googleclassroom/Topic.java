package com.example.googleclassroom;

import java.util.ArrayList;

public class Topic {

    private static final long serialVersionUID = 93074495853L;

    String topicname;
    ArrayList<Assignment> assignments;
    public Topic(String topicname) {
        this.topicname = topicname;
    }

}
