package com.example.googleclassroom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TeacherAdaptor extends RecyclerView.Adapter<TeacherAdaptor.ViewHolder>
{
    private ArrayList<Classrooms> class_list;
    private Context context;
    public TeacherAdaptor(ArrayList<Classrooms> class_list, Context context) {
        this.class_list = class_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Classrooms list_items =class_list.get(i);
        int temp = list_items.getIndex();
        String adds = Integer.toString((temp%9)+1);
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
        viewHolder.headerView.setText(list_items.getName());
        viewHolder.bottomView.setText("teacher dialog");
    }

    @Override
    public int getItemCount() {
        return class_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView headerView;
        public TextView bottomView;
        ImageButton card_back;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.header_card);
            bottomView = itemView.findViewById(R.id.bottom_card);
            card_back = itemView.findViewById(R.id.card_view);
        }
    }
    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }
}
