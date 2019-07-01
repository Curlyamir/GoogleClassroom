package com.example.googleclassroom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    String result = "";
    EditText username;
    //ImageView profile_pic;
    private EditText pass1;
    private EditText pass2;
    private CheckBox check_pass;
    CircleImageView profile_pic;
    Button regeisterbtn;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.usertxt_re);
        pass1 = findViewById(R.id.passtxt1_re);
        pass2 = findViewById(R.id.passtxt2_re);
        check_pass = findViewById(R.id.check_pass_re);
        profile_pic = findViewById(R.id.profile_pic);
        regeisterbtn = findViewById(R.id.main_register);
        regeisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInt = new Intent(getApplicationContext(), main_page.class);
                startActivity(mainInt);
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                String[] str =new String[1];
//                str[0] = username.getText().toString();
//                //send(str , result , "username_register");
//
//                Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
//                if (result.equals("unsuccessful")){
//                    Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
//                }
            }
        });
        pass1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass1.getText().toString().trim().length() <8) {
                    pass1.setError("too short");
                }
            }
        });
        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass1.getText().toString().trim().length() < 8) {
                    pass1.setError("too short");
                }
                if (!pass1.getText().toString().trim().equals(pass2.getText().toString().trim()))
                {
                    pass2.setError("Passwords don't match");
                }
            }
        });
        profile_pic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog();
                return true;
            }


        });

        check_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton button,boolean isChecked)
            {
                if (isChecked)
                {

                    check_pass.setText(R.string.hide_pwd);
                    pass1.setInputType(InputType.TYPE_CLASS_TEXT);
                    pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pass2.setInputType(InputType.TYPE_CLASS_TEXT);
                    pass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    check_pass.setText(R.string.show_pwd);
                    pass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
    private void showDialog()
    {
        final String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog , int item) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profile_pic.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap bmp;
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        try {

                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            bmp = BitmapFactory.decodeStream(imageStream);
                            profile_pic.setImageBitmap(bmp);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        Toast.makeText(this, profile_pic.getDrawable().getIntrinsicWidth() + " & " + profile_pic.getDrawable().getIntrinsicHeight(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "No image picked!", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }


}
