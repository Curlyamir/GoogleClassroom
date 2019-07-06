package com.example.googleclassroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DialogFab extends DialogFragment implements View.OnClickListener {
    User thisUser;
    Class thisClass;
    ClassworkFragment fragment;
    DialogFab(ClassworkFragment fragment)
    {
        this.fragment = fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_class, null);
        Button assign = view.findViewById(R.id.fab_assign);
        Button topic = view.findViewById(R.id.fab_topic);
        Button exam = view.findViewById(R.id.fab_exam);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass");
        assign.setOnClickListener(this);
        topic.setOnClickListener(this);
        exam.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.fab_assign) {

            AssignmentDialog dialg = new AssignmentDialog(fragment);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", thisUser);
            bundle.putSerializable("aClass", thisClass);
            dialg.setArguments(bundle);
            dialg.show(getFragmentManager(), "tag2");
            dismiss();
        }

        if (id == R.id.fab_topic) {
            TopicFragment dialog = new TopicFragment(fragment);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", thisUser);
            bundle.putSerializable("aClass", thisClass);
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "tags2");
            dismiss();
        }
//            case R.id.fab_exam
//                break;
    }

}
