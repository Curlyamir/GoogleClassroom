package com.example.googleclassroom;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClassworkFragment extends Fragment
{
    User thisUser;
    Class thisClass;
    FloatingActionButton fab;
    RecyclerView topics_list ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classwork,container,false);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass") ;
        boolean isTeacher = thisClass.findTeacher(thisUser);
        fab = view.findViewById(R.id.fab_classwork);
        if (!isTeacher)
            fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        topics_list = view.findViewById(R.id.topic_rec_parent);
        LinearLayoutManager temp = new LinearLayoutManager(getContext());
        temp.setOrientation(LinearLayoutManager.HORIZONTAL);
        topics_list.setLayoutManager(temp);
        return view;
    }
    public void openDialog()
    {
        DialogFab dialog = new DialogFab();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",thisUser);
        bundle.putSerializable("aClass",thisClass);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(),"tag");
    }
}
