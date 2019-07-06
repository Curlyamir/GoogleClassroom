package com.example.googleclassroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class PeopleFragment extends Fragment {
    User thisUser;
    Class thisClass;
    RecyclerView recyclerViewStudent;
    RecyclerView recyclerViewTeacher;
    RecyclerView.Adapter adapterstudent;
    RecyclerView.Adapter adapterteacher;
    ImageButton addStu;
    ImageButton addTeach;
    EditText usernamestu;
    EditText usernameteach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass");
        recyclerViewTeacher = view.findViewById(R.id.teachers_name_recView);
        recyclerViewStudent = view.findViewById(R.id.students_name_recView);
        addStu = view.findViewById(R.id.add_students);
        addTeach = view.findViewById(R.id.add_teachers);
        LinearLayoutManager llm1 = new LinearLayoutManager(getContext());
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager llm2 = new LinearLayoutManager(getContext());
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        boolean isTeacher = thisClass.findTeacher(thisUser);
        recyclerViewStudent.setLayoutManager(llm1);
        recyclerViewStudent.setHasFixedSize(true);
        recyclerViewTeacher.setLayoutManager(llm2);
        recyclerViewTeacher.setHasFixedSize(true);
        adapterstudent = new Student_Adaptor(thisClass.students, getContext(), thisUser, thisClass, this);
        recyclerViewStudent.setAdapter(adapterstudent);
        adapterteacher = new Teacher_Adaptor(thisClass.teachers, getContext(), thisUser, thisClass, this);
        recyclerViewTeacher.setAdapter(adapterteacher);
        if (!isTeacher) {
            addTeach.setVisibility(View.INVISIBLE);
            addStu.setVisibility(View.INVISIBLE);
        }
        addStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View vi = inflater.inflate(R.layout.add_students_dialog, null);
                usernamestu = vi.findViewById(R.id.student_username_check);
                builder.setView(vi).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean flagishere = false;
                        for (User stu : thisClass.students) {
                            if (stu.username.equals(usernamestu.getText().toString())) {
                                flagishere = true;
                                System.out.println(stu.username + " stu");
                                System.out.println(usernamestu.getText().toString()+ " edittext");
                                break;
                            }
                        }
                        if (flagishere)
                            Toast.makeText(getContext(), "Student is Already in your class", Toast.LENGTH_SHORT).show();
                        else {
                                AddUsertoClass autc = new AddUsertoClass(PeopleFragment.this);
                                autc.execute("add_stu",thisClass.name,thisUser.username,usernamestu.getText().toString());
                            dialog.dismiss();
                        }


                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        addTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View vi = inflater.inflate(R.layout.add_teacher_dialog, null);
                usernameteach = vi.findViewById(R.id.teacher_username_check);
                builder.setView(vi).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean flagishere = true;
                        for (User teach : thisClass.teachers) {
                            if (teach.username.equals(usernameteach.getText().toString())) {
                                flagishere = true;
                                break;
                            }
                        }
                        if (flagishere)
                            Toast.makeText(getContext(), "Teacher is already in your class", Toast.LENGTH_SHORT).show();
                        else {
                            AddUsertoClass autc = new AddUsertoClass(PeopleFragment.this);
                            autc.execute("add_teach",thisClass.name,thisUser.username,usernameteach.getText().toString());
                            dialog.dismiss();
                        }


                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return view;
    }
}

class AddUsertoClass extends AsyncTask<String, Void, String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<PeopleFragment> activityRefrence;
    byte[] pic;
    Class aClass;
    boolean b;
    String check;
    AddUsertoClass(PeopleFragment context) {
        activityRefrence = new WeakReference<>(context);
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            socket = new Socket("10.0.2.2", 6666);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            check = strings[0];
            out.writeObject(strings);
            out.flush();
             b= in.readBoolean();
            if (b)
            {
                activityRefrence.get().thisUser = (User) in.readObject();
                activityRefrence.get().thisClass = (Class) in.readObject();
            }

            out.close();
            in.close();
            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        PeopleFragment activity = activityRefrence.get();
        if (!b && check.equals("add_stu"))
            activity.usernamestu.setError("Student not found");
        if (!b && check.equals("add_teach"))
            activity.usernameteach.setError("Teacher not found");
        activity.adapterstudent = new Student_Adaptor(activity.thisClass.students, activity.getContext(), activity.thisUser, activity.thisClass, activity);
        activity.recyclerViewStudent.setAdapter(activity.adapterstudent);
        activity.adapterteacher = new Teacher_Adaptor(activity.thisClass.teachers, activity.getContext(), activity.thisUser, activity.thisClass, activity);
        activity.recyclerViewTeacher.setAdapter(activity.adapterteacher);

    }
}
