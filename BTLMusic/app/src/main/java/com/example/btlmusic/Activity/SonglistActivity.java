package com.example.btlmusic.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlmusic.Fragment.Fragment_Account;
import com.example.btlmusic.Fragment.Fragment_Home;
import com.example.btlmusic.Manager.AccountManager;
import com.example.btlmusic.Manager.AlbumManager;
import com.example.btlmusic.Manager.ArtistManager;
import com.example.btlmusic.Manager.ArtistSongManager;
import com.example.btlmusic.Manager.FavoriteManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Account;
import com.example.btlmusic.Opject.Album;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.ArtistSong;
import com.example.btlmusic.Opject.Favorite;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SonglistActivity extends AppCompatActivity {

    ImageButton btnExit,btnFavorite,btnPrev,btnNext,btnPlay;
    TextView txtNameTopSong,txtNameSong,txtNameArtist,txtTimePlus,txtTimeMinus,txtView;
    ImageView imgSong;
    SeekBar seekBar;
    int idSong;
    Song song;
    int idAlbum=0;
    int i=0;
    String SDT,Type ="Song";
    Account account;
    AccountManager accountManager;
    SongManager songManager;
    ArtistSongManager artistSongManager;
    ArtistManager artistManager;
    FavoriteManager favoriteManager;
    AlbumManager albumManager;
    List<Song> list= new ArrayList<>();

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_songlist);
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
        Bundle bundle = intent.getBundleExtra("idSong");
        idSong = bundle.getInt("idSong");
        SDT = bundle.getString("SDT");
        Type = bundle.getString("Type");
        idAlbum = bundle.getInt("idAlbum");
        list=getListSong();
        getAccount();
        playListSong();
        setSong();
        playMusic();
        dragSeekBar();
        clickExit();
        clickNext();
        clickPrev();
        clickPlay();
        setFavorite();
        clickFavorite();

    }
    private void getView(){
        btnExit = findViewById(R.id.songlist_btnExit);
        btnFavorite = findViewById(R.id.songlist_btnFavorite);
        btnPrev = findViewById(R.id.songlist_btnPrev);
        btnPlay = findViewById(R.id.songlist_btnPlay);
        btnNext = findViewById(R.id.songlist_btnNext);
        txtNameTopSong = findViewById(R.id.songlist_txtNameTopSong);
        txtNameSong = findViewById(R.id.songlist_txtNameSong);
        txtNameArtist = findViewById(R.id.songlist_txtNameArtist);
        txtTimePlus = findViewById(R.id.songlist_txtTimePlus);
        txtTimeMinus = findViewById(R.id.songlist_txtTimeMinus);
        imgSong = findViewById(R.id.songlist_imgSong);
        seekBar = findViewById(R.id.songlist_seekBar);
        txtView = findViewById(R.id.songlist_txtViewSong);
    }

    public List<Song> getTypeSong(){
        List<Song> fullSong = new ArrayList<>();

        if(Type.compareTo("Song")==0){

            fullSong= list;
            Log.e("ListSong",fullSong+"");
        }
        if(Type.compareTo("Favorite")==0){
            List<Favorite> favorites = favoriteManager.selectAllFavorite();
            List<Favorite> favoriteList = new ArrayList<>();
            for(Favorite item : favorites){
                if(item.getId_account()==account.getId()){
                    favoriteList.add(item);
                }
            }
            List<Song> songs = songManager.selectAllSong();
            for(Song item : songs){
                for (Favorite item1 : favoriteList){
                    if(item.getId()==item1.getId_song()){
                        fullSong.add(item);
                    }
                }
            }
            for(int i =0 ; i<fullSong.size();i++){
                if(fullSong.get(i).getId()==idSong){
                    Song song1 = fullSong.get(i);
                    fullSong.remove(fullSong.get(i));
                    fullSong.add(0,song1);
                }
            }
            Log.e("Type",fullSong+"");
        }
        if(Type.compareTo("Artist")==0){
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

            //add new song
            List<Song> songList = new ArrayList<>();
            for(ArtistSong item1:artistSongList){
                for(Song item:songs){
                    if(item.getId()==item1.getIdSong()){
                        songList.add(item);
                    }
                }
            }
            fullSong=songList;
        }
        if(Type.compareTo("Album")==0){
            List<Song> songs = songManager.selectAllSong();
            for(Song item:songs){
                if(item.getIdAlbum()==idAlbum){
                    fullSong.add(item);
                }
            }

            HashSet<Integer> idSongSet = new HashSet<>();
            for(Song item : fullSong){
                idSongSet.add(item.getId());
            }
            for (Song item1:list){
                int idSong = item1.getId();
                if(!idSongSet.contains(idSong)){
                    fullSong.add(item1);
                    idSongSet.add(idSong);
                }
            }
            for(int i =0 ; i<fullSong.size();i++){
                if(fullSong.get(i).getId()==idSong){
                    Song song1 = fullSong.get(i);
                    fullSong.remove(fullSong.get(i));
                    fullSong.add(0,song1);
                }
            }

            Log.e("hello",fullSong+"");



        }
        return fullSong;
    }

    public List<Song> getListSong(){
        List<Song> songs = songManager.selectAllSong();
        List<Song> songList = new ArrayList<>();

        for(Song item : songs){
            if(item.getId()==idSong){
                songList.add(item);

            }
        }
        Random random = new Random();
        HashSet<Integer> idSongSet = new HashSet<>();
        idSongSet.add(idSong);
        while (songList.size()<songs.size()) {
            Song newSong = songs.get(random.nextInt(songs.size()));
            if (!idSongSet.contains(newSong.getId())) {
                songList.add(newSong);
                idSongSet.add(newSong.getId());
            }
        }


        return songList;

        /*
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

        //add new song
        List<Song> songList = new ArrayList<>();
        for(ArtistSong item1:artistSongList){
            for(Song item:songs){
                if(item.getId()==item1.getIdSong()){
                    songList.add(item);
                }
            }
        }
        return songList;*/
    }

    private void playListSong() {
        song = getTypeSong().get(i);
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Hủy media player cũ nếu có
        }
        int songResId = getResources().getIdentifier(song.getName(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, songResId);
        seekBar.setMax(mediaPlayer.getDuration());
        txtTimeMinus.setText(formatTime(mediaPlayer.getDuration()));
    }

    private void playMusic(){
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
    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        txtTimePlus.setText(formatTime(mediaPlayer.getCurrentPosition())); // Cập nhật thời gian hiện tại

        if (isPlaying) {
            handler.postDelayed(this::updateSeekBar, 1000);// Cập nhật mỗi giây

        }
    }

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

    private void resetMusic() {
        endMusic();
    }
    private void endMusic(){
        i++;
        playListSong();
        playMusic();
        clickNext();
        setSong();
        seekBar.setProgress(0);
        btnPlay.setImageResource(R.drawable.pause);
        txtTimePlus.setText("00:00"); // Reset thời gian hiện tại
        dragSeekBar();
        playMusic();
    }
    private void stopMusic() {
        mediaPlayer.stop();
        song =getTypeSong().get(i);
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Hủy media player cũ nếu có
        }
        int songResId = getResources().getIdentifier(song.getName(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this,songResId); // Khởi tạo lại MediaPlayer
        seekBar.setProgress(0);
        btnPlay.setImageResource(R.drawable.play);
        txtTimePlus.setText("00:00"); // Reset thời gian hiện tại
        isPlaying = false;
    }

    // Hàm định dạng thời gian (ms) thành dạng mm:ss
    private String formatTime(int millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private void getAccount(){
        List<Account> accounts = accountManager.selectAllAccount();
        for(Account item : accounts){
            if(item.getPhone().compareTo(SDT)==0){
                account=item;
            }
        }
    }

    private void setFavorite(){
        List<Favorite> favorites = favoriteManager.selectAllFavorite();
        List<Favorite> favoriteList = new ArrayList<>();
        for(Favorite item : favorites){
            if(item.getId_account()==account.getId()){
                favoriteList.add(item);
            }
        }

        for(Favorite item : favoriteList){
            if(item.getId_song()==song.getId()){
                btnFavorite.setImageResource(R.drawable.favorite);
                break;
            }
            else {
                btnFavorite.setImageResource(R.drawable.favorite_border);
            }
        }

    }


    //getNameSong
    private void setSong(){
        String img =song.getImg();
        int imageResource = getResources().getIdentifier(img, "drawable", getPackageName());
        if (imageResource != 0) {
            // Gán ảnh vào ImageView nếu tìm thấy
            imgSong.setImageResource(imageResource);
        }
        else {
            // Nếu ảnh không tồn tại, gán ảnh mặc định
            imgSong.setImageResource(R.drawable.logo);
        }
        txtNameSong.setText(updateNameSong().trim());
        txtNameTopSong.setText(updateNameSong().trim());
        txtNameArtist.setText(getNameArtist().trim());
        updatView();
        Log.e("View" ,song.getView()+"");
    }

    private String getNameArtist(){
        List<ArtistSong> artistSongList = artistSongManager.selectAllArtistSong();
        List<Artist> artists = artistManager.selectAllArtist();
        List<ArtistSong> artistSongs = new ArrayList<>();
        for(ArtistSong item : artistSongList){
            if(item.getIdSong()==song.getId()){
                artistSongs.add(item);
            }
        }
        String nameArtist="";
        for(Artist item : artists){
            for(ArtistSong item1: artistSongs){
                if(item.getId()==item1.getIdArtist()){
                    nameArtist +=item.getName().toUpperCase()+" ";
                }
            }
        }
        return  nameArtist;
    }

    private String updateNameSong(){
        String[] name = song.getName().split("_");
        String listName = "";
        for(String item : name){
            listName+=item.toUpperCase()+" ";
        }
        return listName;
    }

    private void clickPlay(){
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
                    i=getTypeSong().size()-1;
                }
                playListSong();
                playMusic();
                setSong();
                dragSeekBar();
                clickExit();
                setFavorite();

            }
        });

    }
    private void updatView(){
        List<Song> songs = songManager.selectAllSong();
        for(Song item : songs){
            if(item.getId()==song.getId()){
                song=item;
            }
        }
        int v =song.getView()+1;
        songManager.updateSong(song.getId(),song.getName(),song.getType(),v,song.getImg(),song.getIdAlbum());
        for(Song item : songs){
            if(item.getId()==song.getId()){
                song=item;
            }
        }
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        // Chuyển số thành chuỗi định dạng
        String vw = numberFormat.format(song.getView());
        Log.e("View",vw+"View");
        txtView.setText(vw);
    }
    private void clickNext(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if(i>getTypeSong().size()-1){
                    i=0;
                }
                song=getTypeSong().get(i);
                playListSong();
                playMusic();
                setSong();
                dragSeekBar();
                clickExit();
                setFavorite();

            }
        });
    }
    private void clickFavorite(){
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Favorite> favorites = favoriteManager.selectAllFavorite();
                List<Favorite> favoriteList = new ArrayList<>();
                for(Favorite item : favorites){
                    if(item.getId_account()==account.getId()){
                        favoriteList.add(item);
                    }
                }
                if(favoriteList.isEmpty()){
                    Favorite favorite = new Favorite(account.getId(),song.getId());
                    try {
                        int i = favoriteManager.insertFavorite(favorite);
                        if(i>0){
                            Toast.makeText(SonglistActivity.this, "Bạn vừa thêm bài hát vào yêu thích", Toast.LENGTH_LONG).show();
                            btnFavorite.setImageResource(R.drawable.favorite);
                        }
                    }catch (Exception e) {
                        Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                        Toast.makeText(SonglistActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                for(Favorite item : favoriteList){
                    if(item.getId_song()!=song.getId()){
                        Favorite favorite = new Favorite(account.getId(),song.getId());
                        try {
                            int i = favoriteManager.insertFavorite(favorite);
                            if (i < 0) {
                                Toast.makeText(SonglistActivity.this, "Lỗi không thêm được vào yêu thích", Toast.LENGTH_LONG).show();
                                Log.e("defaultfavorite", "Thêm thất bại song");
                                break;
                            } else {
                                Toast.makeText(SonglistActivity.this, "Bạn vừa thêm bài hát vào yêu thích", Toast.LENGTH_LONG).show();
                                Log.e("defaultfavorite", "Thêm thành công song");
                                btnFavorite.setImageResource(R.drawable.favorite);
                                break;
                            }
                        } catch (Exception e) {
                            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                            Toast.makeText(SonglistActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                for(Favorite item : favoriteList){
                    if(item.getId_song()==song.getId()){
                        favoriteManager.deleteFavorite(account.getId(),song.getId());
                        Toast.makeText(SonglistActivity.this,"Bạn vừa xóa bài hát khỏi yêu thích",Toast.LENGTH_LONG).show();
                        btnFavorite.setImageResource(R.drawable.favorite_border);
                        break;
                    }
                }
            }
        });
    }


    private void clickExit(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}