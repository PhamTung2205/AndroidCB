package com.example.btlmusic.Manager;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.btlmusic.CreateDatabase.CreateDatabase;
import com.example.btlmusic.Opject.Account;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.Song;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArtistManager {
    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public ArtistManager(Context context){
        this.context = context;
    }

    public ArtistManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }

    public void close(){
        sqlite.close();
    }

    // Lấy ra các Artist
    public List<Artist> selectAllArtist(){
        List<Artist> artistList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_Artist;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Artist_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Artist_Name));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Artist_Img));
                Artist artist = new Artist(id,name,img);
                artistList.add(artist);
            }while (cursor.moveToNext());
        }
        return  artistList;
    }
    public int insertArtist(Artist artist){
        ContentValues values = new ContentValues();
        values.put(sqlite.Artist_Name,artist.getName());
        values.put(sqlite.Artist_Img,artist.getImg());
        Log.e("Artist",artist.getName()+" "+artist.getImg() );
        long result = db.insert(sqlite.Table_Artist,null,values);
        if(result<0){

            return -1;
        }
        return 1;

    }

    public void updateArtist(int id,String name,String img){
        ContentValues values = new ContentValues();
        values.put(sqlite.Artist_Name,name);
        values.put(sqlite.Artist_Img,img);
        db.update(sqlite.Table_Artist,values,sqlite.Artist_Id+"="+id,null);
        if(db.update(sqlite.Table_Artist,values,sqlite.Artist_Id+"="+id,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
    public void deleteArtist(int id){
        db.delete(sqlite.Table_Artist,sqlite.Artist_Id+"="+id,null);
    }

    public List<Artist> selectArtist(String nameArtist){
        List<Artist> artistList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + sqlite.Table_Artist + " WHERE " + sqlite.Artist_Name + " LIKE ?";
        String[] selectionArgs = new String[]{"%"+nameArtist+"%"};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.Artist_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Artist_Name));
                String img = cursor.getString(cursor.getColumnIndexOrThrow(sqlite.Artist_Img));
                Artist artist = new Artist(id,name,img);
                artistList.add(artist);
            }while (cursor.moveToNext());
        }
        return  artistList;
    }

}

