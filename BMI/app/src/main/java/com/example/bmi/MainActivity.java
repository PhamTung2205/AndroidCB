package com.example.bmi;

import android.annotation.SuppressLint;
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
    EditText edtTen , edtChieucao,edtCannang,edtBMI,edtChuandoan;
    Button btnTinh;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtChieucao = findViewById(R.id.edtChieucao);
        edtCannang = findViewById(R.id.edtCannang);
        edtBMI = findViewById(R.id.edtBMI);
        edtChuandoan = findViewById(R.id.edtChuandoan);
        btnTinh = findViewById(R.id.btnTinh);

        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tinh();
            }
        });
    }
    public void Tinh(){
        double Chieucao = Double.valueOf(edtChieucao.getText().toString());
        double Cannang = Double.valueOf(edtCannang.getText().toString());
        double kq = Cannang / (Chieucao*Chieucao) ;
        edtBMI.setText(kq+"");
        if(kq<18){
            edtChuandoan.setText("Người gầy");
        }
        if(kq>18&&kq<25){
            edtChuandoan.setText("Người bình thường");
        }
        if(kq>24.9&&kq<30){
            edtChuandoan.setText("Người béo phì độ I");
        }
        if(kq>29.9&&kq<35){
            edtChuandoan.setText("Người béo phì độ II");
        }
        if(kq>35){
            edtChuandoan.setText("Người béo phì độ III");
        }
    }
}