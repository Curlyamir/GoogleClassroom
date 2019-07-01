package com.example.googleclassroom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CRAdapters extends RecyclerView.Adapter<CRAdapters.ViewHolder>
{
    private ArrayList<Classrooms> class_list;
    private Context context;

    public CRAdapters(ArrayList<Classrooms> class_list, Context context) {
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
            String adds = "@drawable/card" + ((temp%9)+1);
            viewHolder.card_back.setBackground(Drawable.createFromPath("adds"));
            viewHolder.headerView.setText(list_items.getName());
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

}
