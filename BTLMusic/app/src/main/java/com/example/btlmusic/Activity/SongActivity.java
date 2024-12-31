package com.example.btlmusic.Activity;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.btlmusic.Fragment.Fragment_Account;
import com.example.btlmusic.Manager.AccountManager;
import com.example.btlmusic.Manager.AlbumManager;
import com.example.btlmusic.Manager.ArtistManager;
import com.example.btlmusic.Manager.ArtistSongManager;
import com.example.btlmusic.Manager.FavoriteManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Account;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.ArtistSong;
import com.example.btlmusic.Opject.Favorite;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongActivity extends AppCompatActivity {
    ImageButton btnExit,btnFavorite,btnPrev,btnNext,btnPlay;
    TextView txtNameTopSong,txtNameSong,txtNameArtist,txtTimePlus,txtTimeMinus;
    ImageView imgSong;
    SeekBar seekBar;
    Song song;
    Account account;
    MainActivity mainActivity;
    String SDT;
    int i =0;
    int idSong=0,idArtist=0;
    String callerName,callerType;
    SongManager songManager;
    AccountManager accountManager;
    ArtistSongManager artistSongManager;
    ArtistManager artistManager;
    FavoriteManager favoriteManager;
    ArrayList<Object> listSong= new ArrayList<>();

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_song);
        getView();
        try {
            songManager = new SongManager(this);
            songManager.open();
            artistSongManager = new ArtistSongManager(this);
            artistSongManager.open();
            artistManager = new ArtistManager(this);
            artistManager.open();
            favoriteManager = new FavoriteManager(this);
            favoriteManager.open();
            accountManager = new AccountManager(this);
            accountManager.open();
        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(this,"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //SDT = mainActivity.SDT;
        //account = selectAccount(SDT);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("idSong");
        callerType=bundle.getString("CallerType");
        callerName=bundle.getString("CallerName");
        idSong = bundle.getInt("idSong");
        idArtist = bundle.getInt("idArtist");
        getSong();
        playMusic();
        //favoriteSong();
        clickPlay();
        //clickFavorite();
        clickPrev();
        clickNext();
        clickExit();
        dragSeekBar();
        setView();
       Toast.makeText(this,"IdSong click:"+idSong+"   IdSongPlay"+song.getName(),Toast.LENGTH_LONG).show();
    }
    private void getView(){
        btnExit = findViewById(R.id.song_btnExit);
        btnFavorite = findViewById(R.id.song_btnFavorite);
        btnPrev = findViewById(R.id.song_btnPre);
        btnPlay=findViewById(R.id.song_btnPlay);
        btnNext=findViewById(R.id.song_btnNext);
        txtNameTopSong = findViewById(R.id.song_txtNameTopSong);
        txtNameSong = findViewById(R.id.song_txtNameSong);
        txtNameArtist = findViewById(R.id.song_txtNameArtist);
        txtTimePlus=findViewById(R.id.song_txtTimePlus);
        txtTimeMinus=findViewById(R.id.song_txtTimeMinus);
        seekBar = findViewById(R.id.song_seekBar);
        imgSong = findViewById(R.id.song_imgSong);
    }
    private void setView(){
        String img = song.getImg();
        int imageResource = getResources().getIdentifier(img, "drawable",this.getPackageName());

        if (imageResource != 0) {
            // Gán ảnh vào ImageView nếu tìm thấy
            imgSong.setImageResource(imageResource);
        } else {
            // Nếu ảnh không tồn tại, gán ảnh mặc định
            imgSong.setImageResource(R.drawable.logo);
        }
        String[] nameSong = song.getName().split("_");
        String name="";
        for(String item : nameSong){
            name +=item.toUpperCase()+" ";
        }
        txtNameTopSong.setText(name.trim());
        txtNameSong.setText(name.trim());
        txtNameArtist.setText(selectNameArtist());
    }
    public String selectNameArtist(){
        String name="";
        List<ArtistSong> artistSongList = selectArtistSong(song.getId());
        List<Artist> artists = artistManager.selectAllArtist();
        List<String> nameArtist = new ArrayList<>();
        for (Artist item2:artists){
            for (ArtistSong item1: artistSongList){
                if(item2.getId()==item1.getIdArtist()){
                    nameArtist.add(item2.getName());
                }
            }
        }
        for(int a=0;a<nameArtist.size();a++){
            name +=nameArtist.get(a)+" ";
        }

        return name;

    }
    public List<Favorite> selectFavorite(){
        List<Favorite> favorites = favoriteManager.selectAllFavorite();
        List<Favorite> favoriteList= new ArrayList<>();
        for(Favorite item : favorites){
            if(item.getId_account()==account.getId()){
                favoriteList.add(item);
            }
        }
        return favoriteList;
    };

    public Account selectAccount(String sdt){
        List<Account> accounts = accountManager.selectAllAccount();
        Account user = new Account();
        for(Account item : accounts){
            if(item.getPhone().compareTo(sdt)==0){
                user = item;
            }
        }
        return user;
    }

    public void favoriteSong(){
        List<Favorite> favorites = selectFavorite();
        for (Favorite item : favorites){
            if(item.getId_song()==song.getId()){
                btnFavorite.setImageResource(R.drawable.favorite);
            }
            else {
                btnFavorite.setImageResource(R.drawable.favorite_border);
            }
        }
    }
    public void clickFavorite(){
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Favorite> favorites = selectFavorite();
                for (Favorite item : favorites){
                    if (item.getId_song()==song.getId()){
                        favoriteManager.deleteFavorite(account.getId(),song.getId());
                        btnFavorite.setImageResource(R.drawable.favorite_border);
                        Toast.makeText(SongActivity.this,"Bạn vừa bỏ yêu thích bài hát",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Favorite favorite = new Favorite(account.getId(),song.getId());
                        int i = favoriteManager.insertFavorite(favorite);
                        if(i>0){
                            Toast.makeText(SongActivity.this,"Bạn vừa yêu thích bài hát",Toast.LENGTH_LONG).show();
                            btnFavorite.setImageResource(R.drawable.favorite);
                        }
                        else {
                            Toast.makeText(SongActivity.this,"Lỗi không thêm vào được yêu thích",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    public List<ArtistSong> selectArtistSong(int idSong) {
        List<ArtistSong> artistSongs = artistSongManager.selectAllArtistSong();
        List<ArtistSong> listArtist = new ArrayList<>();
        for (ArtistSong item : artistSongs) {
            if (idSong == item.getIdSong()) {
                listArtist.add(item);
            }
        }
        return listArtist;
    }


    public List<Song> playSongs(){
        List<ArtistSong> artistSongs = artistSongManager.selectAllArtistSong();
        List<Song> songs = songManager.selectAllSong();
        List<ArtistSong> listArtistSong = new ArrayList<>();
        for(ArtistSong item :artistSongs){
            if(item.getIdSong()==idSong){
                listArtistSong.add(item);
            }
        }
        List<ArtistSong> artistSongList=new ArrayList<>();

        HashSet<Integer> idSongSet = new HashSet<>();
        for (ArtistSong item : listArtistSong){
            for (ArtistSong item1:artistSongs){
                if(item.getIdArtist()==item1.getIdArtist()){

                    int idSong = item1.getIdSong();
                    if(!idSongSet.contains(idSong)){
                        artistSongList.add(item1);
                        idSongSet.add(idSong);
                    }
                }
            }
        }
        for(ArtistSong item :artistSongs){
            int idSong = item.getIdSong();
            if(!idSongSet.contains(idSong)){
                artistSongList.add(item);
                idSongSet.add(idSong);
            }
        }

        for (int i =0;i<artistSongList.size();i++){
            if(artistSongList.get(i).getIdSong()==idSong){
                if(artistSongList.contains(artistSongList.get(i))){
                    ArtistSong artistSong = artistSongList.get(i);
                    artistSongList.remove(artistSongList.get(i));
                    artistSongList.add(0,artistSong);
                }

            }
        }

        List<Song> songList = new ArrayList<>();
        for(ArtistSong item1:artistSongList){
            for(Song item:songs){
                if(item.getId()==item1.getIdSong()){
                    songList.add(item);
                }
            }
        }
        return songList;
    }
    public void getSong(){
        song = playSongs().get(i);
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Hủy media player cũ nếu có
        }
        int songResId = getResources().getIdentifier(song.getName(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this,songResId);
        seekBar.setMax(mediaPlayer.getDuration());
        txtTimeMinus.setText(formatTime(mediaPlayer.getDuration()));
    }
    public void clickPlay(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            }
        });
    }

    private void clickPrev(){
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;
                if(i<0){
                    i=playSongs().size()-1;
                }
                getSong();
                playMusic();
                //clickFavorite();
                clickExit();
                //favoriteSong();
                clickPlay();
                dragSeekBar();
                setView();
            }
        });

    }
    private void clickNext(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if(i>playSongs().size()-1){
                    i=0;
                }
                getSong();
                playMusic();
                //favoriteSong();
                clickPlay();
                //clickFavorite();
                clickExit();
                dragSeekBar();
                setView();
            }
        });

    }

    //Lỗi
    private void clickExit(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // Dừng nhạc
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                finish();
            }
        });
    }

    //Sử lý kéo
    private void dragSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Cập nhật SeekBar và thời gian phát nhạc
        mediaPlayer.setOnCompletionListener(mp -> resetMusic());
        updateSeekBar();
    }

    private void playMusic() {
        mediaPlayer.start();
        btnPlay.setImageResource(R.drawable.pause);
        isPlaying = true;
        updateSeekBar();
    }
    private void pauseMusic() {
        mediaPlayer.pause();
        btnPlay.setImageResource(R.drawable.play);
        isPlaying = false;
    }

    private void stopMusic() {
        mediaPlayer.stop();
        song = playSongs().get(i);
        int songResId = getResources().getIdentifier(song.getName(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this,songResId); // Khởi tạo lại MediaPlayer
        seekBar.setProgress(0);
        btnPlay.setImageResource(R.drawable.play);
        txtTimePlus.setText("00:00"); // Reset thời gian hiện tại
        isPlaying = false;
    }

    private void resetMusic() {
        stopMusic();
    }
    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        txtTimePlus.setText(formatTime(mediaPlayer.getCurrentPosition())); // Cập nhật thời gian hiện tại

        if (isPlaying) {
            handler.postDelayed(this::updateSeekBar, 1000); // Cập nhật mỗi giây
        }
    }

    private String formatTime(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
        // Dừng Foreground Service

    }
    private void setIdSong(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("idSong");

    }
}