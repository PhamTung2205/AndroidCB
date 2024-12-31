package com.example.btlmusic.Manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.btlmusic.CreateDatabase.CreateDatabase;
import com.example.btlmusic.Opject.Song;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongManager {

    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public SongManager(Context context){
        this.context = context;
    }

    public SongManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }

    public void close(){
        sqlite.close();
    }

    // Lấy ra các Song
    public List<Song> selectAllSong(){
        List<Song> songList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_Song;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Song_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Song_Name));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Song_Type));
                int view = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Song_View));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Song_Img));
                int id_album = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Song_Album_Id));
                Song song = new Song(id,name,type,view,img,id_album);
                songList.add(song);
            }while (cursor.moveToNext());
        }
        return  songList;
    }
    public int insertSong(Song song){
        ContentValues values = new ContentValues();
        values.put(sqlite.Song_Name,song.getName());
        values.put(sqlite.Song_Type,song.getType());
        values.put(sqlite.Song_View,song.getView());
        values.put(sqlite.Song_Img,song.getImg());
        values.put(sqlite.Song_Album_Id,song.getIdAlbum());
        long result = db.insert(sqlite.Table_Song,null,values);
        if(result<0){

            return -1;
        }
        return 1;

    }

    public void updateSong(int id,String name,String type,int view,String img,int id_album){
        ContentValues values = new ContentValues();
        values.put(sqlite.Song_Name,name);
        values.put(sqlite.Song_Type,type);
        values.put(sqlite.Song_View,view);
        values.put(sqlite.Song_Img,img);
        values.put(sqlite.Song_Album_Id,id_album);
        db.update(sqlite.Table_Song,values,sqlite.Song_Id+"="+id,null);
        if(db.update(sqlite.Table_Song,values,sqlite.Song_Id+"="+id,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
    public void deleteSong(int id){
        db.delete(sqlite.Table_Song,sqlite.Song_Id+"="+id,null);
    }
    public List<Song> selectSong(String nameSong){
        List<Song> songList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + sqlite.Table_Song + " WHERE " + sqlite.Song_Name + " LIKE ?";
        String[] selectionArgs = new String[]{"%"+nameSong+"%"};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Song_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Song_Name));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Song_Type));
                int view = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Song_View));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Song_Img));
                int id_album = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Song_Album_Id));
                Song song = new Song(id,name,type,view,img,id_album);
                songList.add(song);
            }while (cursor.moveToNext());
        }
        return  songList;
    }
}

