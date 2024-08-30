package com.example.may_tinh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText txtX , txtY;
    TextView txtResult;
    Button btnPlus,btnMinus,btnCore,btnDivide,btnPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Plus();
        Minus();
        Core();
        Divide();
        Percent();
    }
    private void Plus(){
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtResult=findViewById(R.id.txtResult);
        btnPlus=findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(txtX.getText().toString());
                int y = Integer.parseInt(txtY.getText().toString());
                int result = x+y;
                txtResult.setText(result+"");
            }
        });
    }
    private void Minus(){
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtResult=findViewById(R.id.txtResult);
        btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(txtX.getText().toString());
                int y = Integer.parseInt(txtY.getText().toString());
                int result = x-y;
                txtResult.setText(result+"");
            }
        });
    }
    private void Core(){
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtResult=findViewById(R.id.txtResult);
        btnCore = findViewById(R.id.btnCore);
        btnCore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(txtX.getText().toString());
                int y = Integer.parseInt(txtY.getText().toString());
                int result = x*y;
                txtResult.setText(result+"");
            }
        });
    }
    private void Divide(){
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtResult=findViewById(R.id.txtResult);
        btnDivide = findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(txtX.getText().toString());
                int y = Integer.parseInt(txtY.getText().toString());
                int result = x/y;
                txtResult.setText(result+"");
            }
        });
    }
    private void Percent(){
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtResult=findViewById(R.id.txtResult);
        btnPercent = findViewById(R.id.btnPercent);
        btnPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(txtX.getText().toString());
                int y = Integer.parseInt(txtY.getText().toString());
                int result = x%y;
                txtResult.setText(result+"");
            }
        });
    }

}