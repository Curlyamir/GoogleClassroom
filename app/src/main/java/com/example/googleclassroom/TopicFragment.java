package com.example.googleclassroom;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Calendar;

public class TopicFragment extends AppCompatDialogFragment
{
    EditText editText;
    User thisUser;
    Class thisClass;
    ClassworkFragment fragment;
    TopicFragment(ClassworkFragment fragment)
    {
        this.fragment = fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass") ;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.topic_fragment,null);
        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ToDO add topic(EditText) to topic list

                        Add_topic add_topic = new Add_topic(TopicFragment.this);
                        add_topic.execute("add_topic" , editText.getText().toString() , thisUser.username , thisClass.name);
                        Refresh_classwork ref = new Refresh_classwork(fragment);
                        ref.execute("refresh_classes" , thisUser.username , thisClass.name);
                        dialog.dismiss();
                    }
                });
        editText = view.findViewById(R.id.topic_text);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //ToDo check topic (EditText) already exists or not


                Topic_check topic_check = new Topic_check(TopicFragment.this);
                topic_check.execute("topic_check" , editText.getText().toString() , thisUser.username , thisClass.name);

                //ToDo if exists use setError for Edittext (topic already exists)
            }
        });
        return builder.create();
    }
}

class Add_topic extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<TopicFragment> activityRefrence;
    User user;

    Add_topic(TopicFragment context){
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

            activityRefrence.get().thisUser = (User) in.readObject();
            activityRefrence.get().thisClass = (Class) in.readObject();

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
        TopicFragment activity = activityRefrence.get();


    }
}

class Topic_check extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<TopicFragment> activityRefrence;
    User user;

    Topic_check(TopicFragment context){
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
        TopicFragment activity = activityRefrence.get();

//        if (activity == null || activity.isFinishing()){
//            return;
//        }

        if (!result){
            activityRefrence.get().editText.setError("topic already exists!");
        }

    }
}
