package com.example.myapplicationthi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class HienSinhVien extends AppCompatActivity {
    ListView lvSinhVien;
    SinhVienManager sinhVienManager;
    ArrayList<Object> listSinhVien= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        try {
            sinhVienManager = new SinhVienManager(this);
            sinhVienManager.open();

        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(this,"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_hien_sinh_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvSinhVien = findViewById(R.id.lv_SinhVien);
        defaultListSinhVien();
        clickitem();
    }
    public void defaultListSinhVien(){
        List<SinhVien> songList = sinhVienManager.selectAllSinhVien();


        listSinhVien.addAll(songList);

        ArrayAdapter<Object> nameSinhVien = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listSinhVien);
        lvSinhVien.setAdapter(nameSinhVien);
    }
    public void clickitem(){
        lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectItem = listSinhVien.get(i);
                if(selectItem instanceof SinhVien){
                    SinhVien sinhVien = (SinhVien) selectItem;
                    Intent intent = new Intent(HienSinhVien.this, SuaSinhVien.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",sinhVien.getId());
                    bundle.putString("name",sinhVien.getName());
                    bundle.putString("gioitinh",sinhVien.getGioitinh());
                    bundle.putString("chuyennganh",sinhVien.getChuyennganh());
                    bundle.putDouble("diemtb",sinhVien.getDiemtb());
                    bundle.putString("diachi",sinhVien.getDiachi());
                    intent.putExtra("Sinhvien",bundle);
                    startActivity(intent);
                }
            }
        });
    }
}