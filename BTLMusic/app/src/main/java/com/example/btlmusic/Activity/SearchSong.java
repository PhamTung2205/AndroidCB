package com.example.btlmusic.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlmusic.Manager.AlbumManager;
import com.example.btlmusic.Manager.ArtistManager;
import com.example.btlmusic.Manager.ArtistSongManager;
import com.example.btlmusic.Manager.FavoriteManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.List;

public class SearchSong extends AppCompatActivity {
    EditText edtSearchSong;
    Button btnSearchSong;
    ListView listView;
    SongManager songManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_song);
        getView();
        try {
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
        listSongDefault();
        clickSearchSong();
    }
    public void getView(){
        edtSearchSong =findViewById(R.id.edt_SearchSong);
        btnSearchSong = findViewById(R.id.btn_SearchSong);
        listView = findViewById(R.id.lv_SearchSong);
    }
    public void listSongDefault(){
        List<Song> songList = songManager.selectAllSong();
        ArrayList<String> nameSong= new ArrayList<>();
        for(Song item : songList){
            nameSong.add(item.getName().toString());
        }
        ArrayAdapter<String> nameSong2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,nameSong);
        listView.setAdapter(nameSong2);
    }
    public void addSong(){
        if(edtSearchSong.getText().toString().trim().isEmpty()){
            listSongDefault();
        }
        else {
            List<Song> songList = songManager.selectSong(edtSearchSong.getText().toString().trim());
            ArrayList<String> nameSong = new ArrayList<>();
            for (Song item : songList) {
                nameSong.add(item.getName().toString());
            }
            ArrayAdapter<String> nameSong2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameSong);
            listView.setAdapter(nameSong2);
        }
    }
public void clickSearchSong(){
        btnSearchSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSong();
            }
        });
}


}