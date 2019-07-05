package com.example.googleclassroom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.AsyncTask;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.ArrayList;

public class CRAdapters extends RecyclerView.Adapter<CRAdapters.ViewHolder> {
    private ArrayList<Class> class_list;
    private Context context;
    private User myuser;
    main_page thisContext;

    public CRAdapters(ArrayList<Class> class_list, Context context, User myuser , main_page thisContext) {
        this.class_list = class_list;
        this.context = context;
        this.myuser = myuser;
        this.thisContext = thisContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Class list_items = class_list.get(i);
        int temp = list_items.getIndex();
        String adds = Integer.toString((temp % 9) + 1);
        if (adds.equals("1"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card1);
        if (adds.equals("2"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card2);
        if (adds.equals("3"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card3);
        if (adds.equals("4"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card4);
        if (adds.equals("5"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card5);
        if (adds.equals("6"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card6);
        if (adds.equals("7"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card7);
        if (adds.equals("8"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card8);
        if (adds.equals("9"))
            viewHolder.card_back.setBackgroundResource(R.drawable.card9);
        String tempstr = null;
        final boolean isTeacher = list_items.findTeacher(myuser);
        if (isTeacher) {
            int tempnum = list_items.getStudentsSize();
            tempstr = Integer.toString(tempnum);
            tempstr = tempstr + " students";
        } else {
            tempstr = list_items.teachers.get(0).username.toString();

        }
        viewHolder.headerView.setText(list_items.getName());
        viewHolder.bottomView.setText(tempstr);
        if (isTeacher)
            viewHolder.menupop.add(0, 0, 0, "Edit");
        else
            viewHolder.menupop.add(0, 0, 0, "UnEnroll");
        viewHolder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cardsPopUp.show();
            }
        });
        viewHolder.card_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classIntent = new Intent(context, Classes.class);
                classIntent.putExtra("user", myuser);
                classIntent.putExtra("aClass", list_items);
                context.startActivity(classIntent);
            }

        });
        viewHolder.cardsPopUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                viewHolder.cardsPopUp.dismiss();
                if (isTeacher)
                {
                    //ToDO change Intent to classSetting
                }
                else
                {
                    //ToDo remove student(myuser) from class(list_items)

                    Student_leave student_leave = new Student_leave(thisContext);
                    student_leave.execute("student_leave" , myuser.username , list_items.name);

                }
                return false;
            }

        });

    }

    @Override
    public int getItemCount() {
        return class_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headerView;
        public TextView bottomView;
        ImageButton card_back;
        ImageButton dots;
        PopupMenu cardsPopUp;
        Menu menupop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.header_card);
            bottomView = itemView.findViewById(R.id.bottom_card);
            card_back = itemView.findViewById(R.id.card_view);
            dots = itemView.findViewById(R.id.cards_dot);
            ContextThemeWrapper cmw = new ContextThemeWrapper(context,R.style.CustomPopupTheme);
            cardsPopUp = new PopupMenu(cmw, dots);
            menupop = cardsPopUp.getMenu();
        }
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }
}


class Student_leave extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<main_page> activityRefrence;
    byte[] pic;
    Class aClass;

    Student_leave(main_page context){
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
        main_page activity = activityRefrence.get();

        if (activity == null || activity.isFinishing()){
            return;
        }

        Toast.makeText(activityRefrence.get(), "refreshed", Toast.LENGTH_SHORT).show();
        activity.adapter = new CRAdapters(activity.thisUser.classes,activity.getApplicationContext(),activity.thisUser,activity);
        activity.recyclerView.setAdapter(activity.adapter);

    }
}