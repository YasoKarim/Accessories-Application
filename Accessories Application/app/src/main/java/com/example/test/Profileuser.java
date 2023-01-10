package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Profileuser extends AppCompatActivity {
 Button logout,HomeButton;
 TextInputLayout name,email,phoneno,password;
TextView fullnamelabel,usernamelabel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileuser);
         name =findViewById(R.id.userNameprof);
         email =findViewById(R.id.emailUser);
         phoneno =findViewById(R.id.phoneNumProf);
         password =findViewById(R.id.passworduser);
         fullnamelabel = findViewById(R.id.full_name_label);
         usernamelabel = findViewById(R.id.username_label);
        logout = findViewById(R.id.profile_button);
        //Show all data
        showAllUserData();
        //Log out button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profileuser.this, Log.class);
                startActivity(intent);

            }
        });
        HomeButton = findViewById(R.id.Home_button);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profileuser.this, Home.class);
                startActivity(intent);
            }
        });
    }
    //Show all the user data in the profile page
    private void showAllUserData() {
    Intent intent = getIntent();
    String user_name = intent.getStringExtra("username");
    String email_user = intent.getStringExtra("email");
    String password_user = intent.getStringExtra("password");
    String phone_no = intent.getStringExtra("phoneno");

    fullnamelabel.setText(user_name);
    usernamelabel.setText(user_name);
    name.getEditText().setText(user_name);
    email.getEditText().setText(email_user);
    phoneno.getEditText().setText(phone_no);
    password.getEditText().setText(password_user);

    }

}