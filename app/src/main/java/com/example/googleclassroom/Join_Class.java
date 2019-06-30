package com.example.googleclassroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Join_Class extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join__class);
        toolbar = findViewById(R.id.toolbar_create_class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Class");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_join_class,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cancle_create_class)
        {

        }
        if (item.getItemId() == R.id.join_join_class);
        return super.onOptionsItemSelected(item);
    }
}
