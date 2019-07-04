package com.example.googleclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class Join_Class extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join__class);
        toolbar = findViewById(R.id.toolbar_create_class);
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

        }
        if (item.getItemId() == R.id.join_join_class);
        return super.onOptionsItemSelected(item);
    }
}
class JoinClass extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
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

            result = in.readBoolean();

            if (result) {
                aClass = (Class) in.readObject();
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

        if (result){
            Toast.makeText(activity, "Class Created", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, main_page.class);
            intent.putExtra("aClass" , aClass);
            activity.startActivity(intent);
        }
        else {
            Toast.makeText(activity, "", Toast.LENGTH_LONG).show();
        }

    }
}