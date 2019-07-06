package com.example.googleclassroom;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class Classes extends AppCompatActivity {
    Toolbar toolbar;
    User thisUser;
    Class thisClass;
    boolean isTeacher = true;
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        toolbar = (Toolbar) findViewById(R.id.toolbar_classes);
        thisUser = (User) getIntent().getSerializableExtra("user");
        thisClass = (Class) getIntent().getSerializableExtra("aClass");
        isTeacher = thisClass.findTeacher(thisUser);
        setToolbar();
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav_activity);
        bottom_nav.setOnNavigationItemSelectedListener(navListener);
        if (selectedFragment == null)
        {
            selectedFragment = new ClassworkFragment(this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",thisUser);
            bundle.putSerializable("aClass",thisClass);
            selectedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_bottom_nav,
                    selectedFragment).commit();
        }
        //onBackPressed();
    }
    public void setToolbar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(thisClass.name);
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
                    if (item.getItemId() == R.id.people_bottom_nav)
                    {
                        selectedFragment = new PeopleFragment(Classes.this);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",thisUser);
                        bundle.putSerializable("aClass",thisClass);
                        selectedFragment.setArguments(bundle);
                    }
                    if (item.getItemId() == R.id.classwork_bottom_nav)
                    {
                        selectedFragment = new ClassworkFragment(Classes.this);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",thisUser);
                        bundle.putSerializable("aClass",thisClass);
                        selectedFragment.setArguments(bundle);
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
        if (item.getItemId()==R.id.refresh_classes)
        {
            Refresh_classes refresh_classes = new Refresh_classes(Classes.this);
            refresh_classes.execute("refresh_classes" , thisUser.username , thisClass.name);

        }
        if (item.getItemId() == R.id.info_student)
        {
            Class_Info_Dialog dialog = new Class_Info_Dialog(this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", thisUser);
            bundle.putSerializable("aClass", thisClass);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "tags2");
            return true;
        }
        if (item.getItemId() == R.id.teacher_setting_toolbar)
        {
            Settings_Full_Dialog dialog = new Settings_Full_Dialog(this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", thisUser);
            bundle.putSerializable("aClass", thisClass);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "tags2");
            return true;
        }
        if (item.getItemId() == R.id.about_us_classes) {

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

class Refresh_classes extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<Classes> activityRefrence;
    byte[] pic;
    Class aClass;
    User user;
    Refresh_classes(Classes context){
        activityRefrence = new WeakReference<>(context);
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
//            Toast.makeText(activityRefrence.get(), "pressed in 1", Toast.LENGTH_SHORT).show();
            socket = new Socket("10.0.2.2" , 6666);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

//            Toast.makeText(activityRefrence.get(), "pressed in 2", Toast.LENGTH_SHORT).show();

            out.writeObject(strings);
            out.flush();

            user= (User) in.readObject();
           aClass = (Class) in.readObject();

            out.close();
            in.close();
            socket.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Classes activity = activityRefrence.get();
        activity.thisClass = aClass;
        activity.thisUser = user;
        if (activity == null || activity.isFinishing()){
            return;
        }
        activity.setToolbar();

    }
}