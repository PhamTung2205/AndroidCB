package com.example.btlmusic.CreateDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class CreateDatabase extends SQLiteOpenHelper {
    private static final String DBName ="GPTmusic.db";
    public static final int DBVersion = 1;

    //Tabel Account
    public static final String Table_Account="account";
    public static final String Account_Id = "id";
    public static final String Account_Name = "name";
    public static final String Account_Pass = "pass";
    public static final String Account_Phone = "phone";
    public static final String Account_Img = "img";

    //Table Artist
    public static final String Table_Artist = "artist";
    public static final String Artist_Id = "id";
    public static final String Artist_Name = "name";
    public static final String Artist_Img = "img";


    //Table Song
    public static final String Table_Song = "song";
    public static final String Song_Id = "id";
    public static final String Song_Name = "name";
    public static final String Song_Type = "type";//Thể loại
    public static final String Song_View = "views";
    public static final String Song_Img = "img";
    public static final String Song_Album_Id = "album_id";


    //Song-Artist
    public static final String Table_ArtistSong="artist_song";
    public static final String ArtistSong_Song_Id = "song_id";
    public static final String ArtistSong_Artist_Id = "artist_id";

    //Table Favorite Song Account
    public static final String Table_Favorite = "favorite";
    public static final String Favorite_Song_Id = "song_id";
    public static final String Favorite_Account_Id = "account_id";

    //Table Album
    public static final String Table_Album = "album";
    public static final String Album_Id = "id";
    public static final String Album_Name = "name";
    public static final String Album_Img = "img";
    public static final String Album_Artist_Id = "artist_id";


    //Create Table
    private static final String Create_Table_Account = "CREATE TABLE " + Table_Account + "("+ Account_Id +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +Account_Phone+" TEXT NOT NULL , "+ Account_Name+" TEXT NOT NULL ,"+ Account_Pass + " TEXT NOT NULL , "+ Account_Img+" TEXT NOT NULL)";

    private static final String Create_Table_Artist = "CREATE TABLE "+ Table_Artist + "("+ Artist_Id +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
            Artist_Name + " TEXT NOT NULL , "+ Artist_Img + " TEXT NOT NULL)";

    private static final String Create_Table_Song = "CREATE TABLE "+Table_Song+"("+Song_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Song_Name+" TEXT NOT NULL," +
            Song_Type + " TEXT NOT NULL ," + Song_View+" INTEGER NOT NULL ,"+ Song_Img+" TEXT NOT NULL ,"+Song_Album_Id+" INTEGER NOT NULL ,"+
            "FOREIGN KEY ("+Song_Album_Id+")REFERENCES "+Table_Album+"("+Album_Id+"))";

    private static final String Create_Table_ArtistSong = "CREATE TABLE "+Table_ArtistSong+"( "+ArtistSong_Artist_Id+" INTEGER NOT NULL ," +
            ArtistSong_Song_Id+" INTEGER NOT NULL,"+"PRIMARY KEY( "+ArtistSong_Artist_Id+","+ArtistSong_Song_Id+")," + "FOREIGN KEY ("+ArtistSong_Artist_Id+")REFERENCES "+Table_Artist+"("+Artist_Id+ " ),"+
            "FOREIGN KEY ("+ ArtistSong_Song_Id+")REFERENCES "+Table_Song+"("+Song_Id+"))";


    private static final String Create_Table_Favorite = "CREATE TABLE "+Table_Favorite+"( "+Favorite_Song_Id+" INTEGER NOT NULL," +
            Favorite_Account_Id+" INTEGER NOT NULL,"+"PRIMARY KEY( "+Favorite_Song_Id+", "+Favorite_Account_Id+")," + "FOREIGN KEY ("+Favorite_Song_Id+")REFERENCES "+Table_Song+"("+Song_Id+ " ),"+
            "FOREIGN KEY ("+ Favorite_Account_Id+")REFERENCES "+Table_Account+"("+Account_Id+"))";

    private static final String Create_Table_Album = "CREATE TABLE "+ Table_Album+"("+Album_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Album_Name+" TEXT NOT NULL, "+Album_Img+" TEXT NOT NULL," +
            Album_Artist_Id+" INTEGER NOT NULL," +
            "FOREIGN KEY ("+Album_Artist_Id+")REFERENCES "+Table_Artist+"("+Artist_Id+"))";



    public CreateDatabase(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Account);
        db.execSQL(Create_Table_Artist);
        db.execSQL(Create_Table_Song);
        db.execSQL(Create_Table_ArtistSong);
        db.execSQL(Create_Table_Favorite);
        db.execSQL(Create_Table_Album);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Account);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Artist);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Song);
        db.execSQL("DROP TABLE IF EXISTS " + Table_ArtistSong);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Favorite);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Album);
        onCreate(db);
    }
}
