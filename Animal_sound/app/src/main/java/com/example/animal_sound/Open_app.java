package com.example.animal_sound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Open_app extends AppCompatActivity {
    private int time_out = 1000;
    LinearLayout mainlayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_open_app);

        int[] colors = {
                Color.parseColor("#FF5733"), // Màu cam đậm
                Color.parseColor("#33FF57"), // Màu xanh lá
                Color.parseColor("#3357FF"), // Màu xanh dương
                Color.parseColor("#FF33A5"), // Màu hồng
                Color.parseColor("#33FFF3")  // Màu xanh ngọc
        };
        Random random = new Random();

        mainlayout = findViewById(R.id.Layoutmain);

        mainlayout.setBackgroundColor(colors[random.nextInt(colors.length)]);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent load = new Intent(Open_app.this,item_loading.class);
                startActivity(load);
                finish();
            }
        },time_out);
    }
}