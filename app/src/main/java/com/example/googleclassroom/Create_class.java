package com.example.googleclassroom;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class Create_class extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        toolbar = findViewById(R.id.toolbar_create_class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Class");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cancle_join_class)
        {

        }
        if (item.getItemId() == R.id.create_create_class)
        {
            Intent classInt = new Intent(getApplicationContext(),Classes.class);
            startActivity(classInt);
        }
        return super.onOptionsItemSelected(item);
    }
}
