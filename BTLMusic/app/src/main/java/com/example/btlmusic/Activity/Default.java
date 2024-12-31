package com.example.btlmusic.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlmusic.Manager.AccountManager;
import com.example.btlmusic.Manager.AlbumManager;
import com.example.btlmusic.Manager.ArtistManager;
import com.example.btlmusic.Manager.ArtistSongManager;
import com.example.btlmusic.Manager.FavoriteManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Album;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.ArtistSong;
import com.example.btlmusic.Opject.Favorite;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.List;

public class Default extends AppCompatActivity {
    AlbumManager albumManager;
    ArtistManager artistManager;
    ArtistSongManager artistSongManager;
    FavoriteManager favoriteManager;
    SongManager  songManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_default);
        try {
            artistManager = new ArtistManager(this);
            artistManager.open();
            albumManager = new AlbumManager(this);
            albumManager.open();
            artistSongManager = new ArtistSongManager(this);
            artistSongManager.open();
            favoriteManager = new FavoriteManager(this);
            favoriteManager.open();
            songManager = new SongManager(this);
            songManager.open();

        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(this,"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        insertArtist();
        insertAlbum();
        insertSong();
        insertFavorite();
        insertArtistSong();
    }
    public void insertArtist(){
        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist(1,"Wxrdie","wxrdie"));
        artists.add(new Artist(2,"JustaTee","justatee"));
        artists.add(new Artist(3,"ThaiVG","thai_vg"));
        artists.add(new Artist(4,"QNT","qnt"));
        artists.add(new Artist(5,"Phan Mạnh Quỳnh","phan_manh_quynh"));
        artists.add(new Artist(6,"Sơn Tùng M-TP","son_tung"));
        artists.add(new Artist(7,"Vũ","vu"));
        for (Artist item:artists){
            try {
                int i = artistManager.insertArtist(item);
                if (i < 0) {
                    Toast.makeText(Default.this, "Lỗi không thêm được artist", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Default.this, "Thêm thành công artist", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                Toast.makeText(Default.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void insertAlbum(){
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1,"The Wxrdies","the_wxrdies",1));
        albums.add(new Album(2,"Cine Love","cine_love",5));
        albums.add(new Album(3,"Bảo tàng của sự nuối tiếc","bao_tang_cua_su_nuoi_tiec",7));
        for (Album item : albums){
            try {
                int i = albumManager.insertAlbum(item);
                if (i < 0) {
                    Toast.makeText(Default.this, "Lỗi không thêm được album", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Default.this, "Thêm thành công album", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                Toast.makeText(Default.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void insertSong(){
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1,"nu_cep","RAP",100000,"nu_cep",1));
        songs.add(new Song(2,"get_money","RAP",100000,"get_money",1));
        songs.add(new Song(3,"ca_2","RAP",100000,"ca_2",1));
        songs.add(new Song(4,"tro_ve","RAP",100000,"tro_ve",1));

        songs.add(new Song(5,"nhung_loi_hua_bo_quen","RAP",100000,"nhung_loi_hua_bo_quen",3));
        songs.add(new Song(6,"nhung_chuyen_bay","RAP",100000,"nhung_chuyen_bay",3));
        songs.add(new Song(7,"mua_mua_ay","RAP",100000,"mua_mua_ay",3));
        songs.add(new Song(6,"co_ta","RAP",100000,"co_ta",0));
        songs.add(new Song(8,"buoc_qua_mua_co_don","RAP",100000,"buoc_qua_mua_co_don",0));
        songs.add(new Song(9,"la_lung","RAP",100002,"la_lung",0));
        songs.add(new Song(10,"may_khoc_vi_dieu_gi","RAP",100000,"may_khoc_vi_dieu_gi",3));

        songs.add(new Song(11,"xuan_thi","RAP",100000,"xuan_thi",2));
        songs.add(new Song(12,"ha_lan","RAP",100000,"ha_lan",2));
        songs.add(new Song(13,"da_doan","RAP",100000,"da_doan",0));
        songs.add(new Song(14,"tinh_nho","RAP",100000,"da_doan",0));
        songs.add(new Song(15,"sau_loi_tu_khuoc","RAP",100001,"sau_loi_tu_khuoc",2));
        songs.add(new Song(16,"co_mot_chuyen_tinh","RAP",100000,"co_mot_chuyen_tinh",2));

        songs.add(new Song(17,"theres_no_one_at_all","RAP",100000,"theres_no_one_at_all",0));
        songs.add(new Song(18,"muon_roi_ma_sao_con","RAP",100000,"muon_roi_ma_sao_con",0));
        songs.add(new Song(19,"making_my_way","RAP",100003,"making_my_way",0));
        songs.add(new Song(20,"hay_trao_cho_anh","RAP",100000,"hay_trao_cho_anh",0));
        for(Song item : songs){
            try {
                int i = songManager.insertSong(item);
                if (i < 0) {
                    Toast.makeText(Default.this, "Lỗi không thêm được song", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Default.this, "Thêm thành công song", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                Toast.makeText(Default.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void insertFavorite(){
        List<Favorite> favorites = new ArrayList<>();
        favorites.add(new Favorite(1,2));
        favorites.add(new Favorite(1,3));

        for(Favorite item : favorites){
            try {
                int i = favoriteManager.insertFavorite(item);
                if (i < 0) {
                    Toast.makeText(Default.this, "Lỗi không thêm được favorite", Toast.LENGTH_LONG).show();
                    Log.e("defaultfavorite","Thêm thất bại");
                } else {
                    Toast.makeText(Default.this, "Thêm thành công favorite", Toast.LENGTH_LONG).show();
                    Log.e("defaultfavorite","Thêm thành công");
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                Toast.makeText(Default.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void insertArtistSong(){
        List<ArtistSong> artistSongs = new ArrayList<>();
        artistSongs.add(new ArtistSong(1,1));
        artistSongs.add(new ArtistSong(2,1));
        artistSongs.add(new ArtistSong(2,3));
        artistSongs.add(new ArtistSong(3,1));
        artistSongs.add(new ArtistSong(3,4));
        artistSongs.add(new ArtistSong(4,1));
        artistSongs.add(new ArtistSong(4,2));
        artistSongs.add(new ArtistSong(5,7));
        artistSongs.add(new ArtistSong(6,7));
        artistSongs.add(new ArtistSong(7,7));
        artistSongs.add(new ArtistSong(8,7));
        artistSongs.add(new ArtistSong(9,7));
        artistSongs.add(new ArtistSong(10,7));
        artistSongs.add(new ArtistSong(11,5));
        artistSongs.add(new ArtistSong(12,5));
        artistSongs.add(new ArtistSong(13,5));
        artistSongs.add(new ArtistSong(14,5));
        artistSongs.add(new ArtistSong(15,5));
        artistSongs.add(new ArtistSong(16,5));
        artistSongs.add(new ArtistSong(17,6));
        artistSongs.add(new ArtistSong(18,6));
        artistSongs.add(new ArtistSong(19,6));
        artistSongs.add(new ArtistSong(20,6));
        for(ArtistSong item : artistSongs){
            try {
                int i = artistSongManager.insertArtistSong(item);
                if (i < 0) {
                    Toast.makeText(Default.this, "Lỗi không thêm được artistsong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Default.this, "Thêm thành công artistsong", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                Toast.makeText(Default.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}