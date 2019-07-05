package com.example.googleclassroom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
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
        viewHolder.stu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent classIntent = new Intent(context, Classes.class);
////                classIntent.putExtra("user", myuser);
////                classIntent.putExtra("aClass", list_items);
////                context.startActivity(classIntent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return students_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView stu_img;
        ImageButton card_back;
        ImageButton dots;
        CardView stu_back;
        PopupMenu cardsPopUp;
        TextView stu_name;
        Menu menupop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stu_img = itemView.findViewById(R.id.student_name_pic);
            stu_name = itemView.findViewById(R.id.student_name);
            dots = itemView.findViewById(R.id.dots_student_name);
            stu_back = itemView.findViewById(R.id.student_name_card);
//            cardsPopUp = new PopupMenu(context, dots);
//            menupop = cardsPopUp.getMenu();
        }
    }
}
