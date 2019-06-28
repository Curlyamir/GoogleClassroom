package com.example.socketconnectingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText usernametxt;
    EditText passwordtxt;
    Button button;
    TextView textView;
    Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernametxt = findViewById(R.id.usernametxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        button = findViewById(R.id.loginbtn);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String userpass[] = new String[2];
                userpass[0] = usernametxt.getText().toString();
                userpass[1] = passwordtxt.getText().toString();
                send(userpass);
//                send(passwordtxt.getText().toString());
            }
        });


    }

    public void send(final String[] str){
//        Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();

        SocketConnecting message = new SocketConnecting(textView);
        message.execute(str);

//        return message.doInBackground();



        /*Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2", 6800);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(str);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();*/
    }

}