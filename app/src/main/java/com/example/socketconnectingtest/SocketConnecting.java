package com.example.socketconnectingtest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketConnecting extends AsyncTask < String , Void , Void > {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    @Override
    protected Void doInBackground(String... strs) {

        String m = strs[0];

        try {
            Log.i("socket", "salam");
            socket = new Socket("10.0.2.2", 6800);
            //in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(m);
            socket.close();

        }
        catch (Exception e){

        }

        return null;
    }
}