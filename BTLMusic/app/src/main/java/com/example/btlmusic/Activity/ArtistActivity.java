package com.example.btlmusic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.List;

public class ArtistActivity extends AppCompatActivity {

    ImageButton btnExit;
    ImageView imgArtist;
    TextView txtNameArtist;
    ListView lv_SongArtist;
    int idArtist;
    String SDT;
    Artist artist;
    ArtistManager artistManager;
    ArtistSongManager artistSongManager;
    SongManager songManager;
    AlbumManager albumManager;
    ArrayList<Object> ListSong=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist);
        getView();
        try {
            songManager = new SongManager(this);
            songManager.open();
            artistSongManager = new ArtistSongManager(this);
            artistSongManager.open();
            artistManager = new ArtistManager(this);
            artistManager.open();
            albumManager = new AlbumManager(this);
            albumManager.open();
        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(this,"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("idArtist");
        idArtist = bundle.getInt("idArtist");
        SDT = bundle.getString("SDT");
        selectArtist();
        addlvSongArtist();
        setView();
        clickItem();
        clickExit();
    }
    private void getView(){
        btnExit = findViewById(R.id.artist_btnExit);
        imgArtist = findViewById(R.id.artist_imgArtist);
        txtNameArtist = findViewById(R.id.artist_txtNameArtist);
        lv_SongArtist = findViewById(R.id.artist_lvSongArtist);
    }

    private void setView(){
        String img =artist.getImg();
        int imageResource = getResources().getIdentifier(img, "drawable", getPackageName());
        if (imageResource != 0) {
            // Gán ảnh vào ImageView nếu tìm thấy
            imgArtist.setImageResource(imageResource);
        }
        else {
            // Nếu ảnh không tồn tại, gán ảnh mặc định
            imgArtist.setImageResource(R.drawable.logo);
        }
        txtNameArtist.setText(artist.getName());
    }

    private List<ArtistSong> selectArtistList(){
        List<ArtistSong> artistSongs = artistSongManager.selectAllArtistSong();
        List<ArtistSong> artistSongList = new ArrayList<>();
        for(ArtistSong item : artistSongs){
            if(item.getIdArtist()==idArtist){
                artistSongList.add(item);
            }
        }
        return artistSongList;
    }
    private List<Song> selectSongArtist(){
        List<Song> songs = songManager.selectAllSong();
        List<ArtistSong> artistSongList = selectArtistList();
        List<Song> songList = new ArrayList<>();
        for (Song item:songs){
            for (ArtistSong item1: artistSongList){
                if(item.getId()==item1.getIdSong()){
                    songList.add(item);
                }
            }
        }
        return songList;

    }
    private List<Album> selectAlbumArtist(){
        List<Album> albums = albumManager.selectAllAlbum();
        List<Album> albumList = new ArrayList<>();
        for(Album item : albums){
            if (item.getId_artist() == artist.getId()){
                albumList.add(item);
            }
        }
        return albumList;
    }
    private void addlvSongArtist(){
        List<Song> songs= selectSongArtist();
        List<Album> albums = selectAlbumArtist();
        //List<Song> songs= songManager.selectAllSong();
        ListSong.addAll(songs);
        ListSong.addAll(albums);
        ArrayAdapter<Object> nameSong = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ListSong);
        lv_SongArtist.setAdapter(nameSong);

    }
    private void selectArtist(){
        List<Artist> artists = artistManager.selectAllArtist();
        for(Artist item : artists){
            if(item.getId()==idArtist){
                artist= item;
            }
        }
    }
    private void clickItem(){
        lv_SongArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectItem = ListSong.get(i);
                if(selectItem instanceof Song){
                    Song song = (Song) selectItem;
                    Intent intent = new Intent(ArtistActivity.this, SonglistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idSong",song.getId());
                    bundle.putString("SDT",SDT);
                    Log.e("SDT",SDT);
                    bundle.putString("Type","Artist");
                    intent.putExtra("idSong",bundle);
                    startActivity(intent);
                }
                if(selectItem instanceof Album){
                    Album album = (Album) selectItem;
                    Intent intent = new Intent(ArtistActivity.this, AlbumActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idAlbum",album.getId());
                    bundle.putString("SDT",SDT);
                    intent.putExtra("idAlbum",bundle);
                    startActivity(intent);
                }
            }

        });
    }
    private void clickExit(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}