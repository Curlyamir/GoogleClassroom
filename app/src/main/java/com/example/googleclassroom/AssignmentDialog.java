package com.example.googleclassroom;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AssignmentDialog extends DialogFragment implements View.OnClickListener {
    Calendar myCalendar;
    EditText datetxt;
    EditText assignTitle;
    EditText DesAssign;
    EditText pointPicker;
    EditText TopicPicker;
    EditText titleAssign;
    ImageView attachpic;
    User thisUser;
    Class thisClass;
    EditText timetxt;
    TimePickerDialog timePickerDialog;
    ImageButton close;
    ImageButton complete;
    EditText Topicdialog;
    ClassworkFragment fragment;
    AssignmentDialog(ClassworkFragment fragment)
    {
        this.fragment = fragment;
    }
    byte [] imgbyte;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_fullscreen, null);
        datetxt = view.findViewById(R.id.date_picker);
        timetxt = view.findViewById(R.id.time_picker);
        thisUser = (User) getArguments().getSerializable("user");
        thisClass = (Class) getArguments().getSerializable("aClass") ;
        assignTitle = view.findViewById(R.id.assign_title_dialog);
        DesAssign = view.findViewById(R.id.assign_description_dialog);
        pointPicker = view.findViewById(R.id.points_picker);
        TopicPicker = view.findViewById(R.id.topic_picker);
        close = view.findViewById(R.id.assign_dialog_closeD);
        complete = view.findViewById(R.id.assign_dialog_action);
        ImageButton attachbtn = view.findViewById(R.id.assign_dialog_attach);
        titleAssign = view.findViewById(R.id.assign_title_dialog);
        attachpic = view.findViewById(R.id.assign_dialog_attach_pic);
        myCalendar = Calendar.getInstance();
        datetxt.setOnClickListener(this);
        timetxt.setOnClickListener(this);
        attachbtn.setOnClickListener(this);
        complete.setOnClickListener(this);
        close.setOnClickListener(this);
        TopicPicker.setOnClickListener(this);
        assignTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //ToDO check assignment title

                Title_check title_check = new Title_check(AssignmentDialog.this);
                String topic = TopicPicker.getText().toString().trim();
                if (TopicPicker.getText().toString().trim().equals("")){
                    topic = "no topic";
                }
                title_check.execute("title_check" , titleAssign.getText().toString() , thisUser.username , thisClass.name , topic);

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.topic_picker) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.topic_fragment, null);
            builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
                    .setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TopicPicker.setText(Topicdialog.getText().toString());
                            dialog.dismiss();
                        }
                    });
            Topicdialog = view.findViewById(R.id.topic_text);
            Topicdialog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    //ToDo check topic (TopicDialog) already exists or not


                    //Todo if exists use setError for Topicdialog (topic already exists)
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (v.getId() == R.id.date_picker) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            datetxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v.getId() == R.id.time_picker) {
            myCalendar = Calendar.getInstance();
            final EditText time = v.findViewById(R.id.time_picker);
            int currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = myCalendar.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    time.setText(hourOfDay + ":" + minutes);
                }
            }, currentHour, currentMinute, false);
            timePickerDialog.show();
        }
        if (v.getId() == R.id.assign_dialog_attach) {
            showDialog();
        }
        if (v.getId() == R.id.assign_dialog_action) {
            //ToDo check every needed text in filled
            Create_Assign create_assign = new Create_Assign(AssignmentDialog.this);
            if (attachpic.getDrawable()!=null) {
                Bitmap bmp = ((BitmapDrawable) attachpic.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imgbyte = baos.toByteArray();
            }
            else
            {
                Toast.makeText((getContext()), "No attachment", Toast.LENGTH_SHORT).show();
                imgbyte = new byte[1];
            }
            create_assign.execute("create_assign",thisClass.name,thisUser.username,titleAssign.getText().toString(),DesAssign.getText().toString(),timetxt.getText().toString()
                    ,datetxt.getText().toString(),TopicPicker.getText().toString(),pointPicker.getText().toString(),imgbyte);
            dismiss();
        }
        if (v.getId() == R.id.assign_dialog_closeD) {
            dismiss();
        }
    }

    private void showDialog() {
        final String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        attachpic.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap bmp;
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        try {

                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            bmp = BitmapFactory.decodeStream(imageStream);
                            attachpic.setImageBitmap(bmp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                    }
                    break;
            }
        }
    }
}
class Create_Assign extends AsyncTask<Object , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<AssignmentDialog> activityRefrence;
    User user;

    Create_Assign(AssignmentDialog context){
        activityRefrence = new WeakReference<>(context);
    }


    @Override
    protected String doInBackground(Object... strings) {

        try {
            socket = new Socket("10.0.2.2" , 6666);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            String[] tempstr = new String[]{strings[0].toString() ,strings[1].toString() ,strings[2].toString() , strings[3].toString() , strings[4].toString(),
                    strings[5].toString() , strings[6].toString() , strings[7].toString() , strings[8].toString()};

            out.writeObject(tempstr);
            out.flush();
            out.writeObject(strings[9]);
            out.flush();


            out.close();
            in.close();
            socket.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        AssignmentDialog activity = activityRefrence.get();
        Refresh_classwork ref = new Refresh_classwork(activity.fragment);
        ref.execute("refresh_classes" , activity.thisUser.username , activity.thisClass.name);
    }
}

class Title_check extends AsyncTask<String , Void , String> {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    DataInputStream dataInputStream;
    boolean result;
    WeakReference<AssignmentDialog> activityRefrence;
    User user;

    Title_check(AssignmentDialog context){
        activityRefrence = new WeakReference<>(context);
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
//            Toast.makeText(activityRefrence.get(), "pressed in 1", Toast.LENGTH_SHORT).show();
            socket = new Socket("10.0.2.2" , 6666);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

//            Toast.makeText(activityRefrence.get(), "pressed in 2", Toast.LENGTH_SHORT).show();

            out.writeObject(strings);
            out.flush();

            result = in.readBoolean();

            out.close();
            in.close();
            socket.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        AssignmentDialog activity = activityRefrence.get();

//        if (activity == null || activity.isFinishing()){
//            return;
//        }

        if (!result){
            activityRefrence.get().titleAssign.setError("title already exists!");
        }

    }
}
