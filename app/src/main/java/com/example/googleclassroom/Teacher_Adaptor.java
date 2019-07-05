package com.example.googleclassroom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Teacher_Adaptor extends RecyclerView.Adapter<Teacher_Adaptor.ViewHolder> {
    private ArrayList<User> teachers_list;
    private Context context;
    private Class thisClass;
    private User thisUser;

    public Teacher_Adaptor(ArrayList<User> teachers_list, Context context, User myuser, Class thisClass) {
        this.teachers_list = teachers_list;
        this.context = context;
        this.thisUser = myuser;
        this.thisClass = thisClass;
    }

    @NonNull
    @Override
    public Teacher_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.teacher_layout, viewGroup, false);
        return new Teacher_Adaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Teacher_Adaptor.ViewHolder viewHolder, int i) {
        final User Teacher = teachers_list.get(i);
        if (Teacher.username.equals(thisUser.username) && thisClass.teachers.indexOf(thisUser) == 0)
            viewHolder.dots.setVisibility(View.INVISIBLE);
        if (thisClass.teachers.indexOf(thisUser) != 0 || !thisClass.findTeacher(thisUser))
            viewHolder.dots.setVisibility(View.INVISIBLE);
        viewHolder.teachername.setText(Teacher.username);
        byte[] imgByte = Teacher.picture;
        Bitmap bmp = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        viewHolder.teacher_img.setImageBitmap(bmp);
        viewHolder.menupop.add(0, 0, 0, "Remove");
        viewHolder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.popTeacher.show();
            }
        });
        viewHolder.popTeacher.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                viewHolder.popTeacher.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Sure to remove" + Teacher.username + "From Class??").setTitle("?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        new Thread(){
//                            @Override
//                            public void run() {
//                                super.run();
//                                try {
//                                    Socket s = new Socket(view.getResources().getString(R.string.ip), 8080);
//                                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
//                                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
//
//                                    String[] a = {"RemoveFromClass" , student.username , student.password , cls.code  };
//                                    oos.writeObject(a);
//                                    oos.flush();
//
//                                    oos.close();
//                                    ois.close();
//                                    s.close();
//
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }.start();
//                        RefreshPOE refreshPOE = new RefreshPOE(activity);
//                        refreshPOE.execute("RefreshCLW", user.username, user.password, myclass.code) ;
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }

        });
    }
    @Override
    public int getItemCount() {
        return teachers_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView teacher_img;
        ImageButton dots;
        CardView teacher_back;
        TextView teachername;
        PopupMenu popTeacher;
        Menu menupop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_img = itemView.findViewById(R.id.teacher_name_pic);
            teachername = itemView.findViewById(R.id.teacher_name);
            dots = itemView.findViewById(R.id.dots_teacher_name);
            teacher_back = itemView.findViewById(R.id.teacher_name_card);
            ContextThemeWrapper cmw = new ContextThemeWrapper(context, R.style.CustomPopupTheme);
            popTeacher = new PopupMenu(cmw, dots);
            menupop = popTeacher.getMenu();
        }
    }
}
