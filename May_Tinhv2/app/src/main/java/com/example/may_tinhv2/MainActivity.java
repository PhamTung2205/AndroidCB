package com.example.may_tinhv2;

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
    Button btnTong , btnHieu ,btnTich , btnThuong , btnUocchung , btnThoat;
    EditText edtSoA , edtSoB ,edtKetqua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtSoA = findViewById(R.id.edtSoA);
        edtSoB = findViewById(R.id.edtSoB);
        edtKetqua = findViewById(R.id.edtKetqua);
        btnTong = findViewById(R.id.btnTong);
        btnHieu = findViewById(R.id.btnHieu);
        btnTich = findViewById(R.id.btnTich);
        btnUocchung = findViewById(R.id.btnUocchung);
        btnThuong = findViewById(R.id.btnThuong);
        btnThoat = findViewById(R.id.btnThoat);


        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tong();
            }
        });
        btnHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hieu();
            }
        });
        btnTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tich();
            }
        });
        btnThuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thuong();
            }
        });
        btnUocchung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uocchung();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public  void Tong(){
        int a = Integer.parseInt(edtSoA.getText().toString());
        int b = Integer.parseInt(edtSoB.getText().toString());
        int kq = a+b;
        edtKetqua.setText(kq+"");
    }
    public  void Hieu(){
        int a = Integer.parseInt(edtSoA.getText().toString());
        int b = Integer.parseInt(edtSoB.getText().toString());
        int kq = a-b;
        edtKetqua.setText(kq+"");
    }
    public  void Tich(){
        int a = Integer.parseInt(edtSoA.getText().toString());
        int b = Integer.parseInt(edtSoB.getText().toString());
        int kq = a*b;
        edtKetqua.setText(kq+"");
    }
    public  void Thuong(){
        int a = Integer.parseInt(edtSoA.getText().toString());
        int b = Integer.parseInt(edtSoB.getText().toString());
        int kq = a/b;
        edtKetqua.setText(kq+"");
    }
   public  void Uocchung(){
        int a = Integer.parseInt(edtSoA.getText().toString());
        int b = Integer.parseInt(edtSoB.getText().toString());
        int kq;
        if(a==0||b==0){
            kq=a+b;
        }
        while (a!=b){
            if(a>b){
                a-=b;
            }
            else{
                b-=a;
            }
        }
        kq=a;
        edtKetqua.setText(kq+"");
    }

}