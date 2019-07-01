package com.example.googleclassroom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DialogFab extends DialogFragment implements View.OnClickListener
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_class,null);
        Button assign = view.findViewById(R.id.fab_assign);
        Button topic = view.findViewById(R.id.fab_topic);
        Button exam = view.findViewById(R.id.fab_exam);
        assign.setOnClickListener(this);
        topic.setOnClickListener(this);
        exam.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.fab_assign:
                AssignmentDialog dialg = new AssignmentDialog();
                dialg.show(getFragmentManager(),"tag2");
                dismiss();
                break;

//            case R.id.fab_topic:
//                callback.onActionClick("Whatever");
//                dismiss();
//                break;
//            case R.id.fab_exam
//                break;
        }

    }
}
