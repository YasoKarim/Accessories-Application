package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.util.regex.Pattern;

public class SIgnUp extends AppCompatActivity {
    Button calllogin,regloginbtn;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth auth;
    public static final String TAG = "TAG";
   // TextInputLayout regname,reguser,regemail,regno,regpass;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private TextInputLayout textInputfullname;
    private TextInputLayout textInputpassword;
    private TextInputLayout textInputemail;
    private TextInputLayout textInputNumber;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputfullname = findViewById(R.id.fullname);
        textInputemail = findViewById(R.id.email1);
        textInputpassword = findViewById(R.id.password2);
        textInputNumber = findViewById(R.id.phonenumber);
         auth  = FirebaseAuth.getInstance();

        //to GO to login button
        calllogin = findViewById(R.id.loginbtn);
        calllogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SIgnUp.this,Login.class);
                startActivity(intent);


            }
        });
        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://test-50014-default-rtdb.firebaseio.com/");
        //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //Sign Up button
        //suppress error
        //Put elements in database
        //Authentication of new user and new email is sent
        regloginbtn = findViewById(R.id.signupbtn);

        regloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                 if(!validateUsername()||!validatePhone()||!validateEmail()||!validatepassword()){
                     return;
                 }
                String username =  textInputfullname.getEditText().getText().toString();
                String email    =  textInputemail.getEditText().getText().toString();
                String password =  textInputpassword.getEditText().getText().toString();
                String phoneno  =  textInputNumber.getEditText().getText().toString();
                UserHelperClass helperClass = new UserHelperClass(username,email,phoneno,password);
                reference.child(username).setValue(helperClass);
                confirmInputsignup(view);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SIgnUp.this,"user create",Toast.LENGTH_SHORT).show();
                            UserHelperClass helperClass = new UserHelperClass(username,email,password,phoneno);
                            reference.child(username).setValue(helperClass);
                            Intent intent =new Intent(getApplicationContext(),Login.class);
                            intent.putExtra("email",email);
                            startActivity(intent);
                            //Send the user email for verification
                            FirebaseUser mUser = auth.getCurrentUser();
                            mUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SIgnUp.this,"Vertification has been Sent",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"OnFailure:Email not sent "+e.getMessage());
                                }
                            });

                        }   else{
                            Toast.makeText(SIgnUp.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                Intent intent = new Intent(SIgnUp.this,Home.class);
                startActivity(intent);
            }
        });


    }

//Validate the username
    private boolean validateUsername(){
        String username = textInputfullname.getEditText().getText().toString().trim();
        if(username.isEmpty()) {
            textInputfullname.setError("Field can't be empty");
            return false;
        }
        else if(username.length() > 15){
            textInputfullname.setError("Username too long");
            return false;
        }
        else{
            textInputfullname.setError(null);
            return true;
        }
    }
    //Validate email
    private boolean validateEmail() {
        String emailInput = textInputemail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputemail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputemail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputemail.setError(null);
            return true;
        }
    }
    //Validate Phone Number
    private boolean validatePhone() {
        String phoneno = textInputNumber.getEditText().getText().toString().trim();

        if (phoneno.isEmpty()) {
            textInputNumber.setError("Field can't be empty");
            return false;
        } else if (phoneno.length() < 10) {
            textInputNumber.setError("Please enter a valid phone number");
            return false;
        } else {
            textInputNumber.setError(null);
            return true;
        }
    }
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://test-50014-default-rtdb.firebaseio.com/");
    //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private boolean validatepassword(){
        String passwordinput = textInputpassword.getEditText().getText().toString().trim();

        if(passwordinput.isEmpty()){
            textInputpassword.setError("Field can't be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordinput).matches()) {
            textInputpassword.setError("Password too weak");
            return false;
        } else {
            textInputpassword.setError(null);
            return true;
        }
    }


    //On clicking continue button
    public void confirmInputsignup(View v) {
        if (!validateEmail() || !validateUsername() || !validatepassword() || !validatePhone()) {
            return;
        } else {
            String input = "Email: " + textInputemail.getEditText().getText().toString();
            input += "\n";
            input += "Full Name: " + textInputfullname.getEditText().getText().toString();
            input += "\n";
            input += "Password: " + textInputpassword.getEditText().getText().toString();
        }
    }
}

/*****Draft*************************/
/*
 TextInputLayout email,pass;
        email = findViewById(R.id.email1);
        pass = findViewById(R.id.password2);

        String email2    =  textInputemail.getEditText().getText().toString();
        String password =  textInputpassword.getEditText().getText().toString();
        String username3 =  textInputfullname.getEditText().getText().toString();
      //  String email    =  textInputemail.getEditText().getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(email2,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                    Toast.makeText(SIgnUp.this, "Email Registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").setValue(username3);
                                    databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(email2);
                                    Toast.makeText(SIgnUp.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {

                    }
                });

            }*/

/*ublic boolean number(){

        String phoneno = textInputNumber.getEditText().getText().toString().trim();

        if(phoneno.isEmpty()) {
            textInputNumber.setError("Field can't be empty");
            return false;
    }
        else{
            textInputNumber.setError(null);
            return true;
    }
     Toast.makeText(this,input, Toast.LENGTH_SHORT).show();
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String username =  textInputfullname.getEditText().getText().toString();
                String email =  textInputemail.getEditText().getText().toString();
                String password =  textInputpassword.getEditText().getText().toString();
                String phoneno =  textInputNumber.getEditText().getText().toString();
                UserHelperClass helperClass = new UserHelperClass(username,email,phoneno,password);
                reference.setValue(helperClass);
    }*/
