package com.example.converttocandf;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtDoC,edtDoF;
    Button btnConvertC,btnConvertF,btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edtDoC = findViewById(R.id.edtDoC);
        edtDoF = findViewById(R.id.edtDoF);
        btnConvertC = findViewById(R.id.btnCelsuis);
        btnConvertF = findViewById(R.id.btnFahrenheit);
        btnClear = findViewById(R.id.btnClear);

        btnConvertC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertC();
            }
        });
        btnConvertF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertF();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtDoC.setText("");
                edtDoF.setText("");
            }
        });
    }
    public void convertC(){
        double f = Double.valueOf(edtDoF.getText().toString());
        double c ;

        c = (f-32)*0.5555555555;
        edtDoC.setText(c+"");
    }
    public void convertF(){
        double c = Double.valueOf(edtDoC.getText().toString());

        double f ;

        f = c*1.8+32;
        edtDoF.setText(f+"");
    }
}