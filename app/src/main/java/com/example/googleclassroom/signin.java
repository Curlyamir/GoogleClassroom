package com.example.googleclassroom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class signin extends AppCompatActivity {
    Socket socket;
    EditText username;
    private EditText pass;
    private CheckBox check_pass;
    private Button signbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        check_pass = findViewById(R.id.check_pass_re_in);
        pass= findViewById(R.id.passtxt_in);
        username = findViewById(R.id.usertxt_in);
        signbtn = (Button)findViewById(R.id.main_sign);
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_page_intent = new Intent(getApplicationContext(), main_page.class);
                startActivity(main_page_intent);
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass.getText().toString().trim().length() < 5) {
                    pass.setError("too short");
                }
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
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else {
                    check_pass.setText(R.string.show_pwd);
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


}