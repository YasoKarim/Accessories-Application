package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    private Button button;
    //Animation variables
    Animation topanim,botanimation;
    ImageView image;
    TextView logo,slogan;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to remove status bar and action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        button = (Button) findViewById(R.id.continuebtn);


       //Animation
        topanim = AnimationUtils.loadAnimation(this ,R.anim.anima);
        botanimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //To get the ids of the elements in the page
        image = findViewById(R.id.imageView);
        logo =findViewById(R.id.splashtext);
        slogan =findViewById(R.id.Slogan);

        image.setAnimation(topanim);
        logo.setAnimation(botanimation);
        slogan.setAnimation(botanimation);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(MainActivity.this, Login.class);
           /* startActivity(intent);
            finish();*/
               Pair[] pairs = new Pair[2];
               pairs[0] = new Pair<View,String>(image,"logo");
               pairs[1] = new Pair<View,String>(logo,"logo_text");
               ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
               startActivity(intent,options.toBundle());
           }
       },SPLASH_SCREEN);


}

}

