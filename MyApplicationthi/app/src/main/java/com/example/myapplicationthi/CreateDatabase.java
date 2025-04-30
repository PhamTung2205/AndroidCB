package com.example.myapplicationthi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {
    private static final String DBName ="SinhVien.db";
    public static final int DBVersion = 1;

    //Tabel SinhVien
    public static final String Table_SinhVien="sinhvien";
    public static final String SinhVien_Id = "id";
    public static final String SinhVien_Name = "name";
    public static final String SinhVien_Gt = "gt";
    public static final String SinhVien_ChuyenNganh = "chuyennganh";
    public static final String SinhVien_DiemTB = "DiemTB";
    public static final String SingVien_Diachi = "Diachi";

    private static final String Create_Table_SinhVien = "CREATE TABLE " + Table_SinhVien + "("+ SinhVien_Id +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +SinhVien_Name+" TEXT NOT NULL , "+ SinhVien_Gt+" TEXT NOT NULL ,"+ SinhVien_ChuyenNganh + " TEXT NOT NULL , "+ SinhVien_DiemTB+" REAL NOT NULL ,"+SingVien_Diachi+" TEXT NOT NULL)";

    public CreateDatabase(@Nullable Context context ) {
        super(context,DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_SinhVien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_SinhVien);
    }
}
