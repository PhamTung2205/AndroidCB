package com.example.myapplicationthi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class InsertSinhVien extends AppCompatActivity {

    SinhVienManager sinhVienManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_sinh_vien);
        try {
            sinhVienManager = new SinhVienManager(this);
            sinhVienManager.open();

        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(this,"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        insertSinhVien();
    }
    public void insertSinhVien(){
        List<SinhVien> sinhVienList = new ArrayList<>();
        sinhVienList.add(new SinhVien(1,"Hoàng","Nam","CNTT",6.5,"Hà Nội"));
        sinhVienList.add(new SinhVien(2,"Giang","Nữ","CNTT",7,"Nam Định"));
        sinhVienList.add(new SinhVien(3,"Phong","Nam","Ngân Hàng",6,"Hà Nội"));
        sinhVienList.add(new SinhVien(4,"Thương","Nữ","CNTT",6.5,"Sơn La"));
        sinhVienList.add(new SinhVien(5,"Nam","Nam","Tài Chính",6.5,"Băc Ninh"));
        for (SinhVien item:sinhVienList){
            try {
                int i = sinhVienManager.insertSinhVien(item);
                if (i < 0) {
                    Toast.makeText(InsertSinhVien.this, "Lỗi không thêm được SinhVien", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InsertSinhVien.this, "Thêm thành công SinhVien", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                Toast.makeText(InsertSinhVien.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}