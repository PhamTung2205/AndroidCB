package com.example.animal_sound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnAnimal,btnLoading,btnProfile;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAnimal = findViewById(R.id.Animal_sound);
        /*btnLoading = findViewById(R.id.Item_loading);
        btnProfile = findViewById(R.id.Profile);*/
        btnAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Animail = new Intent(MainActivity.this,Open_app.class);
                startActivity(Animail);
            }
        });
        /*btnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Loading = new Intent(MainActivity.this,item_loading.class);
                startActivity(Loading);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Profile = new Intent(MainActivity.this,profile.class);
                startActivity(Profile);
            }
        });*/
    }
}