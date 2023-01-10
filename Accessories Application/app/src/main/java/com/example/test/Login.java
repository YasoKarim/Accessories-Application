package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private String mCustomToken;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressBar progressBar;

   //Check password pattern

    //Assign an object for each layout of the input box
    private TextInputLayout textInputusername;
    private TextInputLayout textInputpassword;
    Button callSignUp;
    Button profileUser;

    //private FirebaseAuth mAuth;

/***** Coding with tea tutorial *****/

@Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);
       /* To validate an email or password */
       textInputusername = findViewById(R.id.usernamee);
       textInputpassword = findViewById(R.id.password);
       profileUser = findViewById(R.id.continuebtn);

       /* To call the sign up screen on clicking on clicking sign up button from sign up screen  */
       callSignUp = findViewById(R.id.signup_screen);
       //Function to switch between two pages
        //If new account created
       callSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Login.this,SIgnUp.class);
               startActivity(intent);
           }
       });
        //On clicking continue button
       profileUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (!validateUsername() | !validatepassword()) {
                   return;
               }
               else {
                   isUser();
               }

           }
       });




    }
    /**** From codinginflow ****/

    //Check username validation
            private boolean validateUsername() {
                String username = textInputusername.getEditText().getText().toString().trim();
                if (username.isEmpty()) {
                    textInputusername.setError("Field can't be empty");
                    return false;
                }
                 else {
                    textInputusername.setError(null);
                    textInputusername.setErrorEnabled(false);
                    return true;
                }
            }
            //Check password validation
            private boolean validatepassword () {
                String passwordinput = textInputpassword.getEditText().getText().toString().trim();

                if (passwordinput.isEmpty()) {
                    textInputpassword.setError("Field can't be empty");
                    return false;
                }
                else {
                    textInputpassword.setError(null);
                    textInputpassword.setErrorEnabled(false);
                    return true;
                }
            }
            //To check if the user exists or not in the database
            private void isUser ()
            {
                String userEnteredusername = textInputusername.getEditText().getText().toString().trim();
                String userEnteredpassword = textInputpassword.getEditText().getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(userEnteredusername);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    //Check data from firebase database called test
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {

                         if(snapshot.exists())
                         {
                             textInputusername.setError(null);
                             textInputusername.setErrorEnabled(false);
                             String PasswordfromDB = snapshot.child(userEnteredusername).child("password").getValue(String.class);
                         if(PasswordfromDB.equals(userEnteredpassword)){
                             textInputusername.setError(null);
                             textInputusername.setErrorEnabled(false);
                         String usernamefromDB = snapshot.child(userEnteredusername).child("username").getValue(String.class);
                         String emailfromDB = snapshot.child(userEnteredusername).child("email").getValue(String.class);
                         String phonenofromDB = snapshot.child(userEnteredusername).child("phoneno").getValue(String.class);
                         Intent intent = new Intent(getApplicationContext(),Profileuser.class);
                         intent.putExtra("email" ,emailfromDB);
                         intent.putExtra("password",PasswordfromDB);
                         intent.putExtra("phoneno",phonenofromDB);
                         intent.putExtra("username",usernamefromDB);
                         startActivity(intent);
                         }
                         else {
                         textInputpassword.setError("Wrong Password");
                         textInputpassword.requestFocus();
                          }
                         }
                         else{
                            textInputusername.setError("No such user");
                            textInputusername.requestFocus();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());

                    }
                });
            }
        }


/* /*
/*
/*    String input = "Username: " + textInputusername.getEditText().getText().toString();
                   input += "\n";
                   input += "Password: " + textInputpassword.getEditText().getText().toString();

                   //To show a small message to the user for some time
                   Toast.makeText(this, input, Toast.LENGTH_SHORT).show();


  profileUser = findViewById(R.id.continuebtn);
        profileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Login.this,Profileuser.class);
                startActivity(intent);
            }
        });

@Override
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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


else if (!PASSWORD_PATTERN.matcher(passwordinput).matches()) {
                    textInputpassword.setError("Password too weak");
                    return false;
                }@Override

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/
    /*private void startSignIn() {
        // [START sign_in_custom]
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
 mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
                else if (username.length() > 15) {
                    textInputusername.setError("Username too long");
                    return false;*/