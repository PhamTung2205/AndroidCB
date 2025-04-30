package com.example.myapplicationthi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SuaSinhVien extends AppCompatActivity {
    EditText edtId , edtName , edtGT ,edtChuyennganh,edtDiemtb,edtDiachi;
    Button btnSua;
    SinhVienManager sinhVienManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_sinh_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            sinhVienManager = new SinhVienManager(this);
            sinhVienManager.open();

        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(this,"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        edtId = findViewById(R.id.sua_Masinhvien);
        edtName = findViewById(R.id.sua_Tensinhvien);
        edtGT = findViewById(R.id.sua_Gioitinhsinhvien);
        edtChuyennganh = findViewById(R.id.sua_Chuyennganhsinhvien);
        edtDiemtb = findViewById(R.id.sua_Diemtbsinhvien);
        edtDiachi = findViewById(R.id.sua_Daichisinhvien);
        btnSua = findViewById(R.id.btnSuaSinhVien);




        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Sinhvien");
        int id = bundle.getInt("id");
        String name = bundle.getString("name");
        String gt = bundle.getString("gioitinh");
        String cn = bundle.getString("chuyennganh");
        double diembt = bundle.getDouble("diemtb");
        String diachi = bundle.getString("diachi");

        edtId.setText(id+"");
        edtName.setText(name);
        edtGT.setText(gt);
        edtChuyennganh.setText(cn);
        edtDiemtb.setText(diembt+"");
        edtDiachi.setText(diachi);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinhVienManager.updateSinhvien(id,name,gt,cn,diembt,diachi);
            }
        });
    }
}