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

import com.example.btlmusic.Manager.AlbumManager;
import com.example.btlmusic.Manager.ArtistManager;
import com.example.btlmusic.Manager.ArtistSongManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Album;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    String SDT;
    ImageButton btnExit;
    ImageView imgAlbum;
    TextView txtNameAlbum,txtArtistAlbum;
    ListView lvAlbum;
    AlbumManager albumManager;
    ArtistManager artistManager;
    SongManager songManager;
    int idAlbum;
    Album album;
    ArrayList<Object> songName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_album);
        getView();
        try {
            songManager = new SongManager(this);
            songManager.open();
            albumManager = new AlbumManager(this);
            albumManager.open();
            artistManager = new ArtistManager(this);
            artistManager.open();

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
        Bundle bundle = intent.getBundleExtra("idAlbum");
        idAlbum = bundle.getInt("idAlbum");
        SDT =bundle.getString("SDT");
        selectAlbum();
        setView();
        addSong();
        clickItem();
        clickExit();
    }
    private void getView(){
        btnExit = findViewById(R.id.album_btnExit);
        imgAlbum = findViewById(R.id.album_imgAlbum);
        txtNameAlbum = findViewById(R.id.album_txtNameAlbum);
        txtArtistAlbum = findViewById(R.id.album_txtArtistAlbum);
        lvAlbum = findViewById(R.id.album_lvAlbum);
    }
    private void setView(){
        String img =album.getImg();
        int imageResource = getResources().getIdentifier(img, "drawable", getPackageName());
        if (imageResource != 0) {
            // Gán ảnh vào ImageView nếu tìm thấy
            imgAlbum.setImageResource(imageResource);
        }
        else {
            // Nếu ảnh không tồn tại, gán ảnh mặc định
            imgAlbum.setImageResource(R.drawable.logo);
        }
        txtNameAlbum.setText(album.getName());
        txtArtistAlbum.setText(selectNameArtist());
    }

    private void selectAlbum(){
        List<Album> albums = albumManager.selectAllAlbum();
        for(Album item : albums){
            if(item.getId()==idAlbum){
                album = item;
            }
        }

    }
    private String selectNameArtist(){
        List<Artist> artists = artistManager.selectAllArtist();
        String name = "";
        for(Artist item : artists){
            if(item.getId()==album.getId_artist()){
                name = item.getName();
            }
        }
        return name;
    }

    private void addSong(){
        List<Song> songs = songManager.selectAllSong();
        List<Song> songList = new ArrayList<>();
        for (Song item : songs){
            if(item.getIdAlbum()==album.getId()){
                songList.add(item);
            }
        }
        songName.addAll(songList);
        ArrayAdapter<Object> nameSong = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,songName);
        lvAlbum.setAdapter(nameSong);
    }
    private void clickItem(){
        lvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectItem = songName.get(i);
                if(selectItem instanceof Song){
                    Song song = (Song) selectItem;
                    Intent intent = new Intent(AlbumActivity.this, SonglistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idSong",song.getId());
                    bundle.putInt("idAlbum",album.getId());
                    bundle.putString("SDT",SDT);
                    bundle.putString("Type","Album");
                    intent.putExtra("idSong",bundle);
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