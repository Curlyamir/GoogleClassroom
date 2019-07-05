package com.example.googleclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class Join_Class extends AppCompatActivity {
    Toolbar toolbar;
    TextView code;
    User thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join__class);
        toolbar = findViewById(R.id.toolbar_create_class);
        code = findViewById(R.id.class_enter_code);
        thisUser = (User) getIntent().getSerializableExtra("user");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Join Class");
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

            Intent intent = new Intent(getApplicationContext(), main_page.class);
            intent.putExtra("user" , thisUser);
            getApplicationContext().startActivity(intent);

        }
        if (item.getItemId() == R.id.join_join_class){
            JoinClass join = new JoinClass(Join_Class.this);
            join.execute("join_class" , code.getText().toString() , thisUser.username);
        }
        return super.onOptionsItemSelected(item);
    }
}


class JoinClass extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    int result;
    WeakReference<Join_Class> activityRefrence;
    byte[] pic;
    Class aClass;

    JoinClass(Join_Class context){
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

            result = in.readInt();

            if (result == 1) {
                aClass = (Class) in.readObject();
                activityRefrence.get().thisUser = (User) in.readObject();
            }

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
        Join_Class activity = activityRefrence.get();

        if (activity == null || activity.isFinishing()){
            return;
        }

        if (result == 1){
            Toast.makeText(activity, "Class joined", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, Classes.class);
            intent.putExtra("aClass" , aClass);
            intent.putExtra("user" , activity.thisUser);
            activity.startActivity(intent);
        }
        else if (result == 0) {
            Toast.makeText(activity, "class could not found!", Toast.LENGTH_LONG).show();
        }
        else if (result == 2){
            Toast.makeText(activity, "teacher can't join his class!", Toast.LENGTH_LONG).show();
        }
        else if (result == 3) {
            Toast.makeText(activity, "you have already joined this class!", Toast.LENGTH_LONG).show();
        }

    }
}