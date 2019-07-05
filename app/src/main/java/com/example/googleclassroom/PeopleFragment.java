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
import android.widget.LinearLayout;

public class PeopleFragment extends Fragment {
    private User thisUser;
    private Class thisClass;
    RecyclerView recyclerViewStudent;
    RecyclerView recyclerViewTeacher;
    RecyclerView.Adapter adapterstudent;
    RecyclerView.Adapter adapterteacher;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass") ;
        recyclerViewTeacher = view.findViewById(R.id.teachers_name_recView);
        recyclerViewStudent = view.findViewById(R.id.students_name_recView);
        LinearLayoutManager llm1 = new LinearLayoutManager(getContext());
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager llm2 = new LinearLayoutManager(getContext());
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewStudent.setLayoutManager(llm1);
        recyclerViewStudent.setHasFixedSize(true);
        recyclerViewTeacher.setLayoutManager(llm2);
        recyclerViewTeacher.setHasFixedSize(true);
        adapterstudent = new Student_Adaptor(thisClass.students,getContext(),thisUser,thisClass);
        recyclerViewStudent.setAdapter(adapterstudent);
        adapterteacher = new Teacher_Adaptor(thisClass.teachers,getContext(),thisUser,thisClass);
        recyclerViewTeacher.setAdapter(adapterteacher);
        return view;
    }
}
