package com.example.googleclassroom;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class main_page extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar = (Toolbar)findViewById(R.id.toolbar_main_page);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("A&H Classroom");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.create_class)
        {
            Intent createIntent = new Intent(getApplicationContext(),create_class.class);
            startActivity(createIntent);
        }
        if (item.getItemId() == R.id.join_class)
        {
            Intent joinIntent = new Intent(getApplicationContext(),Join_Class.class);
            startActivity(joinIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
