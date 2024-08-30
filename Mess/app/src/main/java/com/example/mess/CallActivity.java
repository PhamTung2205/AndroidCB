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

public class CallActivity extends AppCompatActivity {
    EditText edtNuberphone;
    Button btnBackcall,btnCall;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);
        edtNuberphone = findViewById(R.id.edtNumberphone);
        btnCall = findViewById(R.id.btnCall);
        btnBackcall = findViewById(R.id.btnBackcall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+edtNuberphone.getText().toString()));
                startActivity(callIntent);
            }
        });

        btnBackcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}