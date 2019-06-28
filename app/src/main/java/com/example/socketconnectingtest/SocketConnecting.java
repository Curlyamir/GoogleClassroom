package com.example.socketconnectingtest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnecting extends AsyncTask < String , String , String > {

    Socket socket;
    DataInputStream in;
    ObjectOutputStream out;
    TextView textView;

    public SocketConnecting(TextView textView) {
        this.textView = textView;
    }
//    Context context;
//    SocketConnecting(Context context)
//    {
//        this.context = context;
//    }

//                for (int i = 0; i < str.length; i++) {
//                    System.out.println("Client : " + str[i]);
//                }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strs) {

//        String m = strs[0];

        try {
            Log.i("socket", "salam");
            socket = new Socket("192.168.43.118", 6800);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(strs);
            in = new DataInputStream(socket.getInputStream());
            String temp = in.readUTF();
            textView.setText(temp);
//            Toast.makeText(context,temp,Toast.LENGTH_SHORT).show();


            socket.close();

        }
        catch (Exception e){

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
    }

}