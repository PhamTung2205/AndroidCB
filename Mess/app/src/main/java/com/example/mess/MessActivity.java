package com.example.mess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MessActivity extends AppCompatActivity {
    EditText edtNumbermess;
    Button btnMess,btnBackmess;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mess);
        edtNumbermess = findViewById(R.id.edtNumbermes);
        btnMess = findViewById(R.id.btnMess);
        btnBackmess = findViewById(R.id.btnBackmess);

        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"+edtNumbermess.getText().toString()));
                startActivity(messIntent);
            }
        });
        btnBackmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}