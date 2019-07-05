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

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Adaptor extends RecyclerView.Adapter<Student_Adaptor.ViewHolder> {
    private ArrayList<User> students_list;
    private Context context;
    private Class thisClass;
    private User thisUser;
    public Student_Adaptor(ArrayList<User> Students_list, Context context, User myuser,Class thisClass)
    {
        this.students_list = Students_list;
        this.context = context;
        this.thisUser = myuser;
        this.thisClass = thisClass;
    }
    @NonNull
    @Override
    public Student_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_layout, viewGroup, false);
        return new Student_Adaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Student_Adaptor.ViewHolder viewHolder, int i) {
        final User Student = students_list.get(i);
        viewHolder.stu_name.setText(Student.username);
        byte[] imgByte = Student.picture;
        Bitmap bmp= BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        viewHolder.stu_img.setImageBitmap(bmp);
        if (!thisClass.findTeacher(thisUser))
            viewHolder.dots.setVisibility(View.INVISIBLE);
        viewHolder.menupop.add(0, 0, 0, "Remove");
        viewHolder.stu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent classIntent = new Intent(context, Classes.class);
////                classIntent.putExtra("user", myuser);
////                classIntent.putExtra("aClass", list_items);
////                context.startActivity(classIntent);
            }

        });
        viewHolder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.studentpop.show();
            }
        });
        viewHolder.studentpop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                viewHolder.studentpop.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Sure to remove" + Student.username + "From Class??").setTitle("?");
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
//                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor("");
//                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor("");
                dialog.getContext().setTheme(R.style.AppTheme1);
                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return students_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView stu_img;
        ImageButton dots;
        CardView stu_back;
        PopupMenu studentpop;
        TextView stu_name;
        Menu menupop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stu_img = itemView.findViewById(R.id.student_name_pic);
            stu_name = itemView.findViewById(R.id.student_name);
            dots = itemView.findViewById(R.id.dots_student_name);
            stu_back = itemView.findViewById(R.id.student_name_card);
            ContextThemeWrapper cmw = new ContextThemeWrapper(context, R.style.CustomPopupTheme);
            studentpop = new PopupMenu(cmw, dots);
            menupop = studentpop.getMenu();
        }
    }
}
