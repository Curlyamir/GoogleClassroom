package com.example.googleclassroom;

import androidx.annotation.NonNull;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Class_info extends AppCompatActivity {
    private TextView className;
    private TextView classDis;
    private TextView classRoom;
    private ImageView info_pic;
    private User thisUser;
    private Class thisClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        className = findViewById(R.id.classname_info);
        classDis = findViewById(R.id.dis_txt_info);
        classRoom = findViewById(R.id.room_txt_info);
        info_pic = findViewById(R.id.info_img);
        thisUser = (User) getIntent().getSerializableExtra("user");
        thisClass = (Class) getIntent().getSerializableExtra("aClass");
        classRoom.setText(thisClass.roomNumber);
        className.setText(thisClass.name);
        classDis.setText(thisClass.description);
        if (thisClass.index == 1)
            info_pic.setBackgroundResource(R.drawable.card1);
        if (thisClass.index == 2)
            info_pic.setBackgroundResource(R.drawable.card3);
        if (thisClass.index == 3)
            info_pic.setBackgroundResource(R.drawable.card3);
        if (thisClass.index == 4)
            info_pic.setBackgroundResource(R.drawable.card4);
        if (thisClass.index == 5)
            info_pic.setBackgroundResource(R.drawable.card5);
        if (thisClass.index == 6)
            info_pic.setBackgroundResource(R.drawable.card6);
        if (thisClass.index == 7)
            info_pic.setBackgroundResource(R.drawable.card7);
        if (thisClass.index == 8)
            info_pic.setBackgroundResource(R.drawable.card8);
        if (thisClass.index == 9)
            info_pic.setBackgroundResource(R.drawable.card9);

    }
}
