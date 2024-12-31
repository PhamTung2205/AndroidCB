package com.example.btlmusic.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.btlmusic.CreateDatabase.CreateDatabase;
import com.example.btlmusic.Opject.Album;
import com.example.btlmusic.Opject.Favorite;
import com.example.btlmusic.Opject.Song;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlbumManager {

    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public AlbumManager(Context context){
        this.context = context;
    }

    public AlbumManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }

    public void close(){
        sqlite.close();
    }

    // Lấy ra các Album
    public List<Album> selectAllAlbum(){
        List<Album> albumList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_Album;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Album_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Album_Name));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Album_Img));
                int id_Artist = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Album_Artist_Id));
                Album album = new Album(id,name,img,id_Artist);
                albumList.add(album);
            }while (cursor.moveToNext());
        }
        return  albumList;
    }
    public int insertAlbum(Album album){
        ContentValues values = new ContentValues();
        values.put(sqlite.Album_Id,album.getId());
        values.put(sqlite.Album_Name,album.getName());
        values.put(sqlite.Album_Img,album.getImg());
        values.put(sqlite.Album_Artist_Id,album.getId_artist());
        long result = db.insert(sqlite.Table_Album,null,values);
        if(result<0){

            return -1;
        }
        return 1;

    }

    public void updateAlbum(int id,String name, String img,int id_Artist){
        ContentValues values = new ContentValues();
        values.put(sqlite.Album_Name,name);
        values.put(sqlite.Album_Img,img);
        values.put(sqlite.Album_Artist_Id,id_Artist);
        db.update(sqlite.Table_Album,values,sqlite.Album_Id+"="+id,null);
        if(db.update(sqlite.Table_Album,values,sqlite.Album_Id+"="+id,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
    public void deleteAlbum(int id){
        db.delete(sqlite.Table_Favorite,sqlite.Album_Id+"="+id,null);
    }

    public List<Album> selectAlbum(String nameAlbum){
        List<Album> albumList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + sqlite.Table_Album + " WHERE " + sqlite.Album_Name + " LIKE ?";
        String[] selectionArgs = new String[]{"%"+nameAlbum+"%"};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Album_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Album_Name));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Album_Img));
                int id_artist = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Album_Artist_Id));
                Album album = new Album(id,name,img,id_artist);
                albumList.add(album);
            }while (cursor.moveToNext());
        }
        return  albumList;
    }
}

