package com.example.btlmusic.Manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.btlmusic.CreateDatabase.CreateDatabase;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.ArtistSong;

import java.util.LinkedList;
import java.util.List;

public class ArtistSongManager {
    private SQLiteDatabase db;
    private CreateDatabase sqlite;
    private final Context context;

    public ArtistSongManager(Context context){
        this.context = context;
    }

    public ArtistSongManager open () throws SQLiteException {
        sqlite = new CreateDatabase(this.context);//Tạo database
        db = sqlite.getWritableDatabase();//Cho phép ghi database
        return this;
    }

    public void close(){
        sqlite.close();
    }

    // Lấy ra các ArtistSong
    public List<ArtistSong> selectAllArtistSong(){
        List<ArtistSong> artistSongList = new LinkedList<>();
        String selectQuery = "SELECT * FROM "+ sqlite.Table_ArtistSong;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                int idSong = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.ArtistSong_Song_Id));
                int idArtist = cursor.getInt(cursor.getColumnIndexOrThrow(sqlite.ArtistSong_Artist_Id));

                ArtistSong artistSong = new ArtistSong(idSong,idArtist);
                artistSongList.add(artistSong);
            }while (cursor.moveToNext());
        }
        return  artistSongList;
    }
    public int insertArtistSong(ArtistSong artistSong){
        ContentValues values = new ContentValues();
        values.put(sqlite.ArtistSong_Song_Id,artistSong.getIdSong());
        values.put(sqlite.ArtistSong_Artist_Id,artistSong.getIdArtist());
        long result = db.insert(sqlite.Table_ArtistSong,null,values);
        if(result<0){

            return -1;
        }
        return 1;

    }

    public void updateArtistSong(int idSong,int idArtist){
        ContentValues values = new ContentValues();
        values.put(sqlite.ArtistSong_Song_Id,idSong);
        values.put(sqlite.ArtistSong_Artist_Id,idArtist);
        db.update(sqlite.Table_Artist,values,sqlite.ArtistSong_Song_Id+"="+idSong,null);
        if(db.update(sqlite.Table_Artist,values,sqlite.ArtistSong_Song_Id+"="+idSong,null)<0){

            Toast.makeText(context.getApplicationContext(),"Sửa thất bại",Toast.LENGTH_LONG).show();
            //Sửa không thành công
        }
        Toast.makeText(context.getApplicationContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
        //Sửa thành công
    }
    public void deleteSong(int idSong){
        db.delete(sqlite.Table_ArtistSong,sqlite.ArtistSong_Song_Id+"="+idSong,null);
    }
}

