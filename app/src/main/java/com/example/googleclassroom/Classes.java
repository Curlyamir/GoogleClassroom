package com.example.googleclassroom;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class Classes extends AppCompatActivity {
    Toolbar toolbar;
    User thisUser;
    Class thisClass;
    boolean isTeacher = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        toolbar = (Toolbar) findViewById(R.id.toolbar_classes);
        thisUser = (User) getIntent().getSerializableExtra("user");
        thisClass = (Class) getIntent().getSerializableExtra("aClass");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(thisClass.name);
        isTeacher = thisClass.findTeacher(thisUser);
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav_activity);
        bottom_nav.setOnNavigationItemSelectedListener(navListener);
        //onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent tempInt= new Intent(getApplicationContext(),main_page.class);
            tempInt.putExtra("user",thisUser);
            tempInt.putExtra("aClass",thisClass);
            startActivity(tempInt);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //    @Override
//    public void onBackPressed() {
//        Intent tempInt= new Intent(getApplicationContext(),main_page.class);
//        tempInt.putExtra("user",thisUser);
//        tempInt.putExtra("aClass",thisClass);
//        startActivity(tempInt);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.people_bottom_nav)
                    {
                        selectedFragment = new PeopleFragment();
                    }
                    if (item.getItemId() ==R.id.classwork_bottom_nav)
                    {
                        selectedFragment = new ClassworkFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_bottom_nav,
                            selectedFragment).commit();
                    return true;
                }
            };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_classes,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.refresh_toolbar)
        {
//            Intent createIntent = new Intent(getApplicationContext(), Create_class.class);
//            startActivity(createIntent);
        }
        if (item.getItemId() == R.id.info_student)
        {
            Intent infoIntent = new Intent(getApplicationContext(),Class_info.class);
            infoIntent.putExtra("user",thisUser);
            infoIntent.putExtra("aClass",thisClass);
            startActivity(infoIntent);
        }
        if (item.getItemId() == R.id.teacher_setting_toolbar)
        {
            Intent setIntent = new Intent(getApplicationContext(), Settings_class.class);
            setIntent.putExtra("user",thisUser);
            setIntent.putExtra("aClass",thisClass);
            startActivity(setIntent);
        }
        if (item.getItemId()==R.id.about_us_classes)
        {

        }
        if (item.getItemId() == R.id.notification_classes)
        {

        }
        if (item.getItemId() == R.id.main_page_classes)
        {

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isTeacher)
            menu.findItem(R.id.info_student).setVisible(false);
        else
            menu.findItem(R.id.teacher_setting_toolbar).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
