package com.example.googleclassroom;

import androidx.annotation.NonNull;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Settings_class extends AppCompatActivity {
    private User thisUser;
    private Class thisClass;
    EditText titletxt;
    EditText distxt;
    EditText roomtxt;
    Button savebtn;
    TextView classcode;
    ImageButton closebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_class);
        savebtn = findViewById(R.id.save_setting_class);
        titletxt = (EditText)findViewById(R.id.title_setting_class);
        distxt =findViewById(R.id.dis_setting_class);
        roomtxt = findViewById(R.id.room_setting_class);
        classcode = findViewById(R.id.class_code_setting_class);
        closebtn =findViewById(R.id.close_setting_class);
        thisUser = (User) getIntent().getSerializableExtra("user");
        thisClass = (Class) getIntent().getSerializableExtra("aClass");
        titletxt.setText(thisClass.name);
        distxt.setText(thisClass.description);
        roomtxt.setText(thisClass.roomNumber);
        classcode.setText(thisClass.id);
        thisUser.classes.remove(thisUser.classes.indexOf(thisClass));
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlestr = titletxt.getText().toString();
                String disstr = distxt.getText().toString();
                String roomstr = roomtxt.getText().toString();
                thisClass.name = titlestr;
                thisClass.description = disstr;
                thisClass.roomNumber = roomstr;
                thisUser.classes.add(thisClass);
                Intent backIntent = new Intent(getApplicationContext(),Classes.class);
                backIntent.putExtra("user",thisUser);
                backIntent.putExtra("aClass",thisClass);
                startActivity(backIntent);
            }

        });
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisUser.classes.add(thisClass);
                Intent backIntentq = new Intent(getApplicationContext(),Classes.class);
                backIntentq.putExtra("user",thisUser);
                backIntentq.putExtra("aClass",thisClass);
                startActivity(backIntentq);
            }

        });

    }
}
