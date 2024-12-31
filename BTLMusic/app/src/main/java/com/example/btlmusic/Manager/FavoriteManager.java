package com.example.btlmusic.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.btlmusic.CreateDatabase.CreateDatabase;
import com.example.btlmusic.Opject.Favorite;
import com.example.btlmusic.Opject.Song;

import java.util.LinkedList;
import java.util.List;

public class FavoriteManager {

    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public FavoriteManager(Context context){
        this.context = context;
    }

    public FavoriteManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }

    public void close(){
        sqlite.close();
    }

    // Lấy ra các Favorite
    public List<Favorite> selectAllFavorite(){
        List<Favorite> favoriteList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_Favorite;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int id_Account = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Favorite_Account_Id));
                int id_Song = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Favorite_Song_Id));
                Favorite favorite = new Favorite(id_Account,id_Song);
                favoriteList.add(favorite);
            }while (cursor.moveToNext());
        }
        return  favoriteList;
    }
    public int insertFavorite(Favorite favorite){
        ContentValues values = new ContentValues();

        values.put(sqlite.Favorite_Account_Id,favorite.getId_account());
        values.put(sqlite.Favorite_Song_Id,favorite.getId_song());
        long result = db.insert(sqlite.Table_Favorite,null,values);

        if(result<0){

            return -1;
        }
        return 1;

    }

    public void updateFavorite(int id_Account,int id_Song){
        ContentValues values = new ContentValues();
        values.put(sqlite.Favorite_Song_Id,id_Song);
        db.update(sqlite.Table_Favorite,values,sqlite.Favorite_Account_Id+"="+id_Account,null);
        if(db.update(sqlite.Table_Favorite,values,sqlite.Favorite_Account_Id+"="+id_Account,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
    public void deleteFavorite(int id_Account,int id_Song){
        db.delete(sqlite.Table_Favorite,sqlite.Favorite_Account_Id+"="+id_Account +" AND "+ sqlite.Favorite_Song_Id+"="+id_Song,null);
    }
}
