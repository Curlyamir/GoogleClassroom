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

public class Teacher_Adaptor extends RecyclerView.Adapter<Teacher_Adaptor.ViewHolder> {
    private ArrayList<User> teachers_list;
    private Context context;
    private Class thisClass;
    private User thisUser;
    public  Teacher_Adaptor(ArrayList<User> teachers_list, Context context, User myuser,Class thisClass)
    {
        this.teachers_list = teachers_list;
        this.context = context;
        this.thisUser = myuser;
        this.thisClass = thisClass;
    }
    @NonNull
    @Override
    public  Teacher_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.teacher_layout, viewGroup, false);
        return new  Teacher_Adaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final  Teacher_Adaptor.ViewHolder viewHolder, int i) {
        final User Teacher = teachers_list.get(i);
        viewHolder.teachername.setText(Teacher.username);
        byte[] imgByte = Teacher.picture;
        Bitmap bmp= BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        viewHolder.teacher_img.setImageBitmap(bmp);
        viewHolder.teacher_back.setOnClickListener(new View.OnClickListener() {
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
        return teachers_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView teacher_img;
        ImageButton dots;
        CardView teacher_back;
        PopupMenu cardsPopUp;
        TextView teachername;
        Menu menupop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_img = itemView.findViewById(R.id.teacher_name_pic);
            teachername = itemView.findViewById(R.id.teacher_name);
            dots = itemView.findViewById(R.id.dots_teacher_name);
            teacher_back = itemView.findViewById(R.id.teacher_name_card);
//            cardsPopUp = new PopupMenu(context, dots);
//            menupop = cardsPopUp.getMenu();
        }
    }
}
