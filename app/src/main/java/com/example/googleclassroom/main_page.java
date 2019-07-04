package com.example.googleclassroom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class main_page extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Class thisClass;
    Toolbar toolbar;
    User thisUser;
    private String[] activityTitles;
    public static int navItemIndex = 0;
    private Handler mHandler;
    private ArrayList<Classrooms> class_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        thisUser = (User) getIntent().getSerializableExtra("user");
        Bitmap userpic = BitmapFactory.decodeByteArray(thisUser.picture,0,thisUser.picture.length);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main_page);
        thisClass = (Class) getIntent().getSerializableExtra("aClass");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("A&H Classroom");
        //mHandler = new Handler();
        drawer =  findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtWebsite =  navHeader.findViewById(R.id.website);
        imgNavHeaderBg =  navHeader.findViewById(R.id.img_header_bg);
        imgProfile =  navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CRAdapters(thisUser.classes,this);
        recyclerView.setAdapter(adapter);
        setUpNavigationView();
        imgProfile.setImageBitmap(userpic);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    private void loadNavHeader() {
//        txtName.setText("getName");
//        txtWebsite.setText("nake");

//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);
//
//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }
    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_notifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NotificationFragment()).commit();
                        break;
                    case R.id.nav_settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();
                        break;
                    case R.id.nav_about_us:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AboutUSFragment()).commit();
                        break;
                    case R.id.nav_classes_home:
                        Intent tempInt = new Intent(getApplicationContext(), main_page.class);
                        startActivity(tempInt);
                        return true;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
            Intent createIntent = new Intent(getApplicationContext(), Create_class.class);
            createIntent.putExtra("user" , thisUser);
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
