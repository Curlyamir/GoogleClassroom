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

public class Settings_Full_Dialog extends DialogFragment implements View.OnClickListener {
    EditText classTitle;
    EditText classDes;
    EditText classRoom;
    User thisUser;
    Class thisClass;
    ImageButton close;
    Button saveSetting;
    TextView codeClass;
    Classes fragment;

    Settings_Full_Dialog(Classes fragment) {
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
        View view = inflater.inflate(R.layout.activity_settings_class, container, false);
        classTitle = view.findViewById(R.id.title_setting_class);
        classDes = view.findViewById(R.id.dis_setting_class);
        classRoom = view.findViewById(R.id.room_setting_class);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass");
        close = view.findViewById(R.id.close_setting_class);
        saveSetting = view.findViewById(R.id.save_setting_class);
        codeClass = view.findViewById(R.id.class_code_setting_class);
        classTitle.setText(thisClass.name);
        classDes.setText(thisClass.description);
        classRoom.setText(thisClass.roomNumber);
        codeClass.setText(thisClass.id);
        close.setOnClickListener(this);
        saveSetting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_setting_class) {
            dismiss();
        }
        if (id == R.id.save_setting_class) {
            String titlestr = classTitle.getText().toString();
            String disstr = classDes.getText().toString();
            String roomstr = classRoom.getText().toString();
            Update_Class upd = new Update_Class(this);
            upd.execute("update_class", thisClass.name, thisUser.username, titlestr, disstr, roomstr);
            dismiss();
        }

    }

}

class Update_Class extends AsyncTask<String, Void, String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<Settings_Full_Dialog> activityRefrence;
    User user;

    Update_Class(Settings_Full_Dialog context) {
        activityRefrence = new WeakReference<>(context);
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            socket = new Socket("10.0.2.2", 6666);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(strings);
            out.flush();
            activityRefrence.get().thisUser = (User) in.readObject();
            activityRefrence.get().thisClass = (Class) in.readObject();

            out.close();
            in.close();
            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Settings_Full_Dialog activity = activityRefrence.get();

        Refresh_classes rfc = new Refresh_classes(activity.fragment);
        rfc.execute("refresh_classes", activity.thisUser.username, activity.thisClass.name);
    }
}