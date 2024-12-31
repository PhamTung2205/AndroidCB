package com.example.btlmusic.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.btlmusic.CreateDatabase.CreateDatabase;
import com.example.btlmusic.Opject.Account;

import java.util.LinkedList;
import java.util.List;

public class AccountManager {
    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public AccountManager(Context context){
        this.context = context;
    }
    public AccountManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }
    public void close(){
        sqlite.close();
    }

    //Lấy tất cả account
    public List<Account> selectAllAccount(){
        List<Account> accountList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_Account;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Account_Id));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Account_Phone));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Account_Name));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Account_Pass));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Account_Img));
                Account account = new Account(id,phone,name,pass,img);
                accountList.add(account);
            }while (cursor.moveToNext());
        }

        return  accountList;
    }

    public int insertAccount(Account account){
        ContentValues values = new ContentValues();
        values.put(sqlite.Account_Phone,account.getPhone());
        values.put(sqlite.Account_Name,account.getName());
        values.put(sqlite.Account_Pass,account.getPass());
        values.put(sqlite.Account_Img,account.getImg());
        long result = db.insert(sqlite.Table_Account,null,values);
        if(result<0){

            return -1;//Không thêm được
        }
        return 1;//thêm được

    }
    public void updateAccount(int id,String name , String pass, String phone,String img){
        ContentValues values = new ContentValues();
        values.put(sqlite.Account_Phone,phone);
        values.put(sqlite.Account_Name,name);
        values.put(sqlite.Account_Pass,pass);
        values.put(sqlite.Account_Img,img);
        db.update(sqlite.Table_Account,values,sqlite.Account_Id+"="+id,null);
        if(db.update(sqlite.Table_Account,values,sqlite.Account_Id+"="+id,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
    public void deleteAccount(int id){
        db.delete(sqlite.Table_Account,sqlite.Account_Id+"="+id,null);
    }
}
