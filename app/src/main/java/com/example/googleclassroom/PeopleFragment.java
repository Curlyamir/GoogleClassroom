package com.example.googleclassroom;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PeopleFragment extends Fragment {
    private User thisUser;
    private Class thisClass;
    RecyclerView recyclerViewStudent;
    RecyclerView recyclerViewTeacher;
    RecyclerView.Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass") ;
        recyclerViewTeacher = view.findViewById(R.id.teachers_name_recView);
        recyclerViewStudent = view.findViewById(R.id.students_name_recView);
        recyclerViewStudent.setHasFixedSize(true);
        recyclerViewStudent.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTeacher.setHasFixedSize(true);
        recyclerViewTeacher.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Student_Adaptor(thisClass.students,getContext(),thisUser,thisClass);
        recyclerViewStudent.setAdapter(adapter);
        adapter = new Teacher_Adaptor(thisClass.teachers,getContext(),thisUser,thisClass);
        recyclerViewTeacher.setAdapter(adapter);
        return inflater.inflate(R.layout.fragment_people,container,false);
    }
}
