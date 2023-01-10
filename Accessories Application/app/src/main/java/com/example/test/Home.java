package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose");
        getMenuInflater().inflate(R.menu.menu,menu);
    }
     int homeitem1 =  R.id.Homeitem;
    int profileitem1 = R.id.Profileitem;
    @SuppressLint("NonConstantResourceId")
   //Main menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId())
    {
        case R.id.Homeitem:
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            return  true;
        case R.id.Profileitem:
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            return  true;
        default:
            return super.onContextItemSelected(item);
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView imageView = findViewById(R.id.main_menu);
        registerForContextMenu(imageView);

    }
}