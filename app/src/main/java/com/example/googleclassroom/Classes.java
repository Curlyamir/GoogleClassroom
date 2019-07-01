package com.example.googleclassroom;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Classes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav_activity);
        bottom_nav.setOnNavigationItemSelectedListener(navListener);
    }
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
}
