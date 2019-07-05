package com.example.googleclassroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class Signin extends AppCompatActivity {
    Socket socket;
    EditText username;
    private EditText pass;
    private CheckBox check_pass;
    private Button signbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        check_pass = findViewById(R.id.check_pass_re_in);
        pass= findViewById(R.id.passtxt_in);
        username = findViewById(R.id.usertxt_in);
        signbtn = (Button)findViewById(R.id.main_sign);
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
//                Intent main_page_intent = new Intent(getApplicationContext(), main_page.class);
//                startActivity(main_page_intent);

                String USERNAME = username.getText().toString();
                String PASSWORD = pass.getText().toString();
                String[] toSend = new String[]{"sign_in" , USERNAME , PASSWORD};

//                Toast.makeText(getApplicationContext(), "pressed 2", Toast.LENGTH_SHORT).show();

                Signin_check signin_check = new Signin_check(Signin.this);
                signin_check.execute(toSend);

//                Toast.makeText(getApplicationContext(), "pressed 3", Toast.LENGTH_SHORT).show();

            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass.getText().toString().trim().length() < 5) {
                    pass.setError("too short");
                }
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (username.getText().toString().length() > 0) {
                    Username_check username_check = new Username_check(Signin.this);
                    username_check.execute("username_check", username.getText().toString());
                }
            }
        });
        check_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton button,boolean isChecked)
            {
                if (isChecked)
                {

                    check_pass.setText(R.string.hide_pwd);
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else {
                    check_pass.setText(R.string.show_pwd);
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }



}

class Signin_check extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<Signin> activityRefrence;
    User user;

    Signin_check(Signin context){
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

            if (result){
                user = (User) in.readObject();
                System.out.println(user.username);
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
        Signin activity = activityRefrence.get();

        if (activity == null || activity.isFinishing()){
            return;
        }

        if (result){
            Toast.makeText(activity, "You're Logged in Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, main_page.class);
            intent.putExtra("user" , user);
            activity.startActivity(intent);
        }else{
            Toast.makeText(activity, "Wrong Username Or Password !", Toast.LENGTH_LONG).show();
        }

    }
}


class Username_check extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<Signin> activityRefrence;
    User user;

    Username_check(Signin context){
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
        Signin activity = activityRefrence.get();

        if (activity == null || activity.isFinishing()){
            return;
        }

        if (!result){
            activity.username.setError("Username Not Found!");
        }

    }
}