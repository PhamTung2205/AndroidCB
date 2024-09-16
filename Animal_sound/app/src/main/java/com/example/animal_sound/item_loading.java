package com.example.animal_sound;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class item_loading extends AppCompatActivity {
    private  int time_out =1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent load = new Intent(item_loading.this,profile.class);
                startActivity(load);
                finish();
            }
        },time_out);
    }
}