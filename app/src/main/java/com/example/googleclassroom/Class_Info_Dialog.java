package com.example.googleclassroom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class Class_Info_Dialog extends DialogFragment implements View.OnClickListener {
    TextView className;
    TextView classDis;
    TextView classRoom;
    ImageView info_pic;
    User thisUser;
    Class thisClass;
    ImageButton close;
    Classes fragment;
    Class_Info_Dialog(Classes fragment)
    {
        this.fragment = fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_class_info, container, false);
        className = view.findViewById(R.id.classname_info);
        classDis = view.findViewById(R.id.dis_txt_info);
        classRoom = view.findViewById(R.id.room_txt_info);
        info_pic = view.findViewById(R.id.info_img);
        close = view.findViewById(R.id.close_info_class);
        classRoom.setText(thisClass.roomNumber);
        className.setText(thisClass.name);
        classDis.setText(thisClass.description);
        if (thisClass.index == 1)
            info_pic.setBackgroundResource(R.drawable.card1);
        if (thisClass.index == 2)
            info_pic.setBackgroundResource(R.drawable.card3);
        if (thisClass.index == 3)
            info_pic.setBackgroundResource(R.drawable.card3);
        if (thisClass.index == 4)
            info_pic.setBackgroundResource(R.drawable.card4);
        if (thisClass.index == 5)
            info_pic.setBackgroundResource(R.drawable.card5);
        if (thisClass.index == 6)
            info_pic.setBackgroundResource(R.drawable.card6);
        if (thisClass.index == 7)
            info_pic.setBackgroundResource(R.drawable.card7);
        if (thisClass.index == 8)
            info_pic.setBackgroundResource(R.drawable.card8);
        if (thisClass.index == 9)
            info_pic.setBackgroundResource(R.drawable.card9);
        close.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_info_class)
        {
            dismiss();
        }
    }

}