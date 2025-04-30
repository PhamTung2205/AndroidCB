package com.example.myapplicationthi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class SinhVienManager {
    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public SinhVienManager(Context context) {
        this.context = context;
    }
    public SinhVienManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }
    public void close(){
        sqlite.close();
    }

    public List<SinhVien> selectAllSinhVien(){
        List<SinhVien> songList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_SinhVien;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.SinhVien_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.SinhVien_Name));
                String gioitinh = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.SinhVien_Gt));
                String chuyennganh = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.SinhVien_ChuyenNganh));
                double diemtb = cursor.getDouble(cursor.getColumnIndexOrThrow(sqlite.SinhVien_DiemTB));
                String diachi = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.SingVien_Diachi));
                SinhVien song = new SinhVien(id,name,gioitinh,chuyennganh,diemtb,diachi);
                songList.add(song);
            }while (cursor.moveToNext());
        }
        return  songList;
    }
    public int insertSinhVien(SinhVien sinhvien){
        ContentValues values = new ContentValues();
        values.put(sqlite.SinhVien_Name,sinhvien.getName());
        values.put(sqlite.SinhVien_Gt,sinhvien.getGioitinh());
        values.put(sqlite.SinhVien_ChuyenNganh,sinhvien.getChuyennganh());
        values.put(sqlite.SinhVien_DiemTB,sinhvien.getDiemtb());
        values.put(sqlite.SingVien_Diachi,sinhvien.getDiachi());
        long result = db.insert(sqlite.Table_SinhVien,null,values);
        if(result<0){

            return -1;
        }
        return 1;

    }
    public void updateSinhvien(int id, String name, String gioitinh, String chuyennganh, double diemtb, String diachi){
        ContentValues values = new ContentValues();
        values.put(sqlite.SinhVien_Name,name);
        values.put(sqlite.SinhVien_Gt,gioitinh);
        values.put(sqlite.SinhVien_ChuyenNganh,chuyennganh);
        values.put(sqlite.SinhVien_DiemTB,diemtb);
        values.put(sqlite.SingVien_Diachi,diachi);
        db.update(sqlite.Table_SinhVien,values,sqlite.SinhVien_Id+"="+id,null);
        if(db.update(sqlite.Table_SinhVien,values,sqlite.SinhVien_Id+"="+id,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
}
