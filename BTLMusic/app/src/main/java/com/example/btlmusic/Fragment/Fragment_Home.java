package com.example.btlmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlmusic.Activity.AlbumActivity;
import com.example.btlmusic.Activity.ArtistActivity;
import com.example.btlmusic.Activity.MainActivity;
import com.example.btlmusic.Activity.SongActivity;
import com.example.btlmusic.Activity.SonglistActivity;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Home extends Fragment {
    int idtopsongtask1,idtopsongtask2,idtopsongtask3,idartisttask1,idartisttask2,idartisttask3,idalbumtask1,idalbumtask2,idalbumtask3;
    String SDT="";
    MainActivity mainActivity;
    SongManager songManager;
    ArtistSongManager artistSongManager;
    ArtistManager artistManager;
    AlbumManager albumManager;
    FavoriteManager favoriteManager;
    AccountManager accountManager;
    LinearLayout ltTopSongTask1,ltTopSongTask2,ltTopSongTask3,ltArtistTask1,ltArtistTask2,ltArtistTask3,ltAlbumTask1,ltAlbumTask2,ltAlbumTask3;
    ImageView ivTopSongTask1,ivTopSongTask2,ivTopSongTask3,ivArtistTask1,ivArtistTask2,ivArtistTask3,ivAlbumTask1,ivAlbumTask2,ivAlbumTask3;
    TextView txtNameTopSongTask1,txtNameTopSongTask2,txtNameTopSongTask3,txtArtistTopSongTask1,txtArtistTopSongTask2,txtArtistTopSongTask3,txtNameArtistTask1,txtNameArtistTask2,txtNameArtistTask3,txtNameSongAlbum1,txtNameSongAlbum2,txtNameSongAlbum3,txtArtistAlbumTask1,txtArtistAlbumTask2,txtArtistAlbumTask3;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Home newInstance(String param1, String param2) {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getView(view);
        try {
            songManager = new SongManager(getContext());
            songManager.open();
            artistSongManager = new ArtistSongManager(getContext());
            artistSongManager.open();
            artistManager = new ArtistManager(getContext());
            artistManager.open();
            favoriteManager = new FavoriteManager(getContext());
            favoriteManager.open();
            accountManager = new AccountManager(getContext());
            accountManager.open();
            albumManager = new AlbumManager(getContext());
            albumManager.open();

        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(getContext(),"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        mainActivity = (MainActivity) getActivity();
        SDT = mainActivity.SDT;
        addTopSong();
        addArtist();
        addAlbum();
        clickTopSongTask1();
        clickTopSongTask2();
        clickTopSongTask3();
        clickArtistTask1();
        clickArtistTask2();
        clickArtistTask3();
        clickAlbumTask1();
        clickAlbumTask2();
        clickAlbumTask3();
        /*clickFavoriteTask1();
        clickFavoriteTask2();
        clickFavoriteTask3();*/
        return view;
    }
    public void getView(View view){
        //TOP Song
        ltTopSongTask1 = view.findViewById(R.id.fragHome_TopSongTask1);
        ivTopSongTask1 = view.findViewById(R.id.fragHome_TopSongTask1Img);
        txtNameTopSongTask1 = view.findViewById(R.id.fragHome_TopSongTask1NameSong);
        txtArtistTopSongTask1 = view.findViewById(R.id.fragHome_TopSongTask1NameArtist);

        ltTopSongTask2 = view.findViewById(R.id.fragHome_TopSongTask2);
        ivTopSongTask2 = view.findViewById(R.id.fragHome_TopSongTask2Img);
        txtNameTopSongTask2 = view.findViewById(R.id.fragHome_TopSongTask2NameSong);
        txtArtistTopSongTask2 = view.findViewById(R.id.fragHome_TopSongTask2NameArtist);

        ltTopSongTask3 = view.findViewById(R.id.fragHome_TopSongTask3);
        ivTopSongTask3 = view.findViewById(R.id.fragHome_TopSongTask3Img);
        txtNameTopSongTask3 = view.findViewById(R.id.fragHome_TopSongTask3NameSong);
        txtArtistTopSongTask3 = view.findViewById(R.id.fragHome_TopSongTask3NameArtist);

        //Artist
        ltArtistTask1 = view.findViewById(R.id.fragHome_TopArtistTask1);
        ivArtistTask1 = view.findViewById(R.id.fragHome_TopArtistTask1Img);
        txtNameArtistTask1 = view.findViewById(R.id.fragHome_TopArtistTask1NameArtist);

        ltArtistTask2 = view.findViewById(R.id.fragHome_TopArtistTask2);
        ivArtistTask2 = view.findViewById(R.id.fragHome_TopArtistTask2Img);
        txtNameArtistTask2 = view.findViewById(R.id.fragHome_TopArtistTask2NameArtist);

        ltArtistTask3 = view.findViewById(R.id.fragHome_TopArtistTask3);
        ivArtistTask3 = view.findViewById(R.id.fragHome_TopArtistTask3Img);
        txtNameArtistTask3 = view.findViewById(R.id.fragHome_TopArtistTask3NameArtist);

        //Album
        ltAlbumTask1 = view.findViewById(R.id.fragHome_AlbumTask1);
        ivAlbumTask1 = view.findViewById(R.id.fragHome_AlbumTask1Img);
        txtNameSongAlbum1 = view.findViewById(R.id.fragHome_AlbumTask1NameSong);
        txtArtistAlbumTask1 = view.findViewById(R.id.fragHome_AlbumTask1NameArtist);

        ltAlbumTask2 = view.findViewById(R.id.fragHome_AlbumTask2);
        ivAlbumTask2 = view.findViewById(R.id.fragHome_AlbumTask2Img);
        txtNameSongAlbum2 = view.findViewById(R.id.fragHome_AlbumTask2NameSong);
        txtArtistAlbumTask2 = view.findViewById(R.id.fragHome_AlbumTask2NameArtist);

        ltAlbumTask3 = view.findViewById(R.id.fragHome_AlbumTask3);
        ivAlbumTask3 = view.findViewById(R.id.fragHome_AlbumTask3Img);
        txtNameSongAlbum3 = view.findViewById(R.id.fragHome_AlbumTask3NameSong);
        txtArtistAlbumTask3 = view.findViewById(R.id.fragHome_AlbumTask3NameArtist);

    }



    //Top Song
    public void addTopSong(){
        List<Song> songs = slectTopSong();
        for(int i=0;i<songs.size();i++ ){
            String img = songs.get(i).getImg();
            String nameSong = songs.get(i).getName();
            String[] listName = nameSong.split("_");
            List<String> nameArtist = selectNameArtist(songs.get(i).getId());
            String name ="";
            String Songname ="";
            if(i==0){
                idtopsongtask1= songs.get(i).getId();
                for(int a=0;a<nameArtist.size();a++){
                    name +=nameArtist.get(a)+" ";
                    txtArtistTopSongTask1.setText(name);
                }
                for(int a=0 ;a<listName.length;a++){

                    if(listName.length<13){
                        Songname +=listName[a].toUpperCase()+" ";
                    }
                    else {
                        Songname+="...";
                        break;
                    }
                }
                txtNameTopSongTask1.setText(Songname);
                 // Lấy tên ảnh từ đối tượng
                int imageResource = getResources().getIdentifier(img, "drawable",getContext().getPackageName());

                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivTopSongTask1.setImageResource(imageResource);
                } else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivTopSongTask1.setImageResource(R.drawable.logo);
                }
            }
            if(i==1){
                idtopsongtask2= songs.get(i).getId();
                for(int a=0;a<nameArtist.size();a++){
                    name +=nameArtist.get(a)+" ";
                    txtArtistTopSongTask2.setText(name);
                }
                for(int a=0 ;a<listName.length;a++){

                    if(listName.length<13){
                        Songname +=listName[a].toUpperCase()+" ";
                    }
                    else {
                        Songname+="...";
                        break;
                    }
                }
                txtNameTopSongTask2.setText(Songname);
                // Lấy tên ảnh từ đối tượng
                int imageResource = getResources().getIdentifier(img, "drawable",getContext().getPackageName());

                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivTopSongTask2.setImageResource(imageResource);
                } else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivTopSongTask2.setImageResource(R.drawable.logo);
                }
            }
            if(i==2){
                idtopsongtask3= songs.get(i).getId();
                for(int a=0;a<nameArtist.size();a++){
                    name +=nameArtist.get(a)+" ";
                    txtArtistTopSongTask3.setText(name);
                }
                for(int a=0 ;a<listName.length;a++){
                    if(listName.length<13){
                    Songname +=listName[a].toUpperCase()+" ";
                    }
                    else {
                        Songname+="...";
                        break;
                    }
                }
                txtNameTopSongTask3.setText(Songname);
                // Lấy tên ảnh từ đối tượng
                int imageResource = getResources().getIdentifier(img, "drawable",getContext().getPackageName());

                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivTopSongTask3.setImageResource(imageResource);
                } else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivTopSongTask3.setImageResource(R.drawable.logo);
                }
            }

        }

    }
    //SelectTopSong
    public List<Song> slectTopSong(){
        List<Song> songs = songManager.selectAllSong();;
        for (int i=0;i<songs.size();i++){
            for (int j=i+1;j<songs.size();j++){
                if(songs.get(i).getView()<songs.get(j).getView()){
                    Song song = songs.get(i);
                    songs.set(i,songs.get(j));
                    songs.set(j,song);
                }
            }
        }

        return songs;
    }
    //Select ArtistSong
    public List<ArtistSong> selectArtistSong(int idSong){
        List<ArtistSong> artistSongs = artistSongManager.selectAllArtistSong();
        List<ArtistSong> listArtist = new ArrayList<>();
        for (ArtistSong item : artistSongs){
            if(idSong==item.getIdSong()){
                listArtist.add(item);
            }
        }
        return listArtist;
    }
    //Select NameArtist
    public List<String> selectNameArtist(int idSong){
        List<ArtistSong> artistSongList = selectArtistSong(idSong);
        List<Artist> artists = artistManager.selectAllArtist();
        List<String> nameArtist = new ArrayList<>();
        for (Artist item2:artists){
            for (ArtistSong item1: artistSongList){
                if(item2.getId()==item1.getIdArtist()){
                    nameArtist.add(item2.getName());
                }
            }
        }

        return nameArtist;
    }

    //top Artart
    public void addArtist(){
        Random random = new Random();
        List<Artist> artists = artistManager.selectAllArtist();
        List<Artist> artistList = new ArrayList<>();
        HashSet<Integer> idArtistSet = new HashSet<>();
        while (artistList.size()<artists.size()){
            Artist artist = artists.get(random.nextInt(artists.size()));

            if(!idArtistSet.contains(artist.getId())){
                artistList.add(artist);
                idArtistSet.add(artist.getId());
            }

        }

        for (int i=0;i<artistList.size();i++){
            String img = artistList.get(i).getImg();
            String nameArtist =artistList.get(i).getName();
            if(i==0) {
                idartisttask1 = artistList.get(i).getId();
                txtNameArtistTask1.setText(nameArtist);
                // Lấy tên ảnh từ đối tượng
                int imageResource = getResources().getIdentifier(img, "drawable", getContext().getPackageName());

                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivArtistTask1.setImageResource(imageResource);
                } else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivArtistTask1.setImageResource(R.drawable.logo);
                }
            }
            if(i==6) {
                idartisttask2 = artistList.get(i).getId();
                txtNameArtistTask2.setText(nameArtist);
                // Lấy tên ảnh từ đối tượng
                int imageResource = getResources().getIdentifier(img, "drawable", getContext().getPackageName());

                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivArtistTask2.setImageResource(imageResource);
                } else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivArtistTask2.setImageResource(R.drawable.logo);
                }
            }
            if(i==4) {
                idartisttask3 = artistList.get(i).getId();
                txtNameArtistTask3.setText(nameArtist);
                // Lấy tên ảnh từ đối tượng
                int imageResource = getResources().getIdentifier(img, "drawable", getContext().getPackageName());

                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivArtistTask3.setImageResource(imageResource);
                } else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivArtistTask3.setImageResource(R.drawable.logo);
                }
            }
        }
    }

    public void addAlbum(){
        Random random = new Random();
        List<Album> albums = albumManager.selectAllAlbum();
        List<Album> albumList = new ArrayList<>();
        HashSet<Integer> idAlbumSet = new HashSet<>();
        while (albumList.size()<albums.size()){
            Album album = albums.get(random.nextInt(albums.size()));

            if(!idAlbumSet.contains(album.getId())){
                albumList.add(album);
                idAlbumSet.add(album.getId());
            }

        }

        for(int i=0;i<albumList.size();i++){
            int idAlbum = albumList.get(i).getId();
            String img = albumList.get(i).getImg();
            String nameAlbum = albumList.get(i).getName();
            int idArtist = albumList.get(i).getId_artist();
            Artist artist = selectnameArtist(idArtist);
            if(i==0){
                idalbumtask1 = idAlbum;
                int imageResource = getResources().getIdentifier(img, "drawable", getContext().getPackageName());
                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivAlbumTask1.setImageResource(imageResource);
                }
                else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivAlbumTask1.setImageResource(R.drawable.logo);
                }
                if(nameAlbum.length()>13){
                    nameAlbum=nameAlbum.substring(0,13)+"...";
                }

                txtNameSongAlbum1.setText(nameAlbum);

                txtArtistAlbumTask1.setText(artist.getName());
            }
            if(i==1){
                idalbumtask2 = idAlbum;
                int imageResource = getResources().getIdentifier(img, "drawable", getContext().getPackageName());
                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivAlbumTask2.setImageResource(imageResource);
                }
                else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivAlbumTask2.setImageResource(R.drawable.logo);
                }
                if(nameAlbum.length()>13){
                    nameAlbum=nameAlbum.substring(0,13)+"...";
                }
                txtNameSongAlbum2.setText(nameAlbum);
                txtArtistAlbumTask2.setText(artist.getName());
            }
            if(i==2){
                idalbumtask3 = idAlbum;
                int imageResource = getResources().getIdentifier(img, "drawable", getContext().getPackageName());
                if (imageResource != 0) {
                    // Gán ảnh vào ImageView nếu tìm thấy
                    ivAlbumTask3.setImageResource(imageResource);
                }
                else {
                    // Nếu ảnh không tồn tại, gán ảnh mặc định
                    ivAlbumTask3.setImageResource(R.drawable.logo);
                }
                if(nameAlbum.length()>13){
                    nameAlbum=nameAlbum.substring(0,13)+"...";
                }
                txtNameSongAlbum3.setText(nameAlbum);
                txtArtistAlbumTask3.setText(artist.getName());
            }
        }

    }
    public Artist selectnameArtist(int id){
        List<Artist> artists = artistManager.selectAllArtist();
        Artist artist =new Artist();
        for(Artist item : artists){
            if(item.getId()==id){
                artist=item;
            }
        }
        return artist;
    }

    //Click topSong
    public void clickTopSongTask1(){
        ltTopSongTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SonglistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idSong",idtopsongtask1);
                bundle.putString("SDT",SDT);
                bundle.putString("Type","Song");
                intent.putExtra("idSong",bundle);
                startActivity(intent);
            }
        });
    }

    public void clickTopSongTask2(){
        ltTopSongTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SonglistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idSong",idtopsongtask2);
                bundle.putString("SDT",SDT);
                bundle.putString("Type","Song");
                intent.putExtra("idSong",bundle);
                startActivity(intent);
            }
        });
    }
    public void clickTopSongTask3(){
        ltTopSongTask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SonglistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idSong",idtopsongtask3);
                bundle.putString("SDT",SDT);
                bundle.putString("Type","Song");
                intent.putExtra("idSong",bundle);
                startActivity(intent);
            }
        });
    }

    //Artist
    public void clickArtistTask1(){
        ltArtistTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ArtistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idArtist",idartisttask1);
                bundle.putString("SDT",SDT);
                intent.putExtra("idArtist",bundle);
                startActivity(intent);
            }
        });
    }
    public void clickArtistTask2(){
        ltArtistTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ArtistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idArtist",idartisttask2);
                bundle.putString("SDT",SDT);
                intent.putExtra("idArtist",bundle);
                startActivity(intent);
            }
        });
    }
    public void clickArtistTask3(){
        ltArtistTask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ArtistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idArtist",idartisttask3);
                bundle.putString("SDT",SDT);
                intent.putExtra("idArtist",bundle);
                startActivity(intent);
            }
        });
    }

    //clickAlbum
    public void clickAlbumTask1(){
        ltAlbumTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idAlbum",idalbumtask1);
                bundle.putString("SDT",SDT);
                intent.putExtra("idAlbum",bundle);
                startActivity(intent);
            }
        });
    }
    public void clickAlbumTask2(){
        ltAlbumTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idAlbum",idalbumtask2);
                bundle.putString("SDT",SDT);
                intent.putExtra("idAlbum",bundle);
                startActivity(intent);
            }
        });
    }
    public void clickAlbumTask3(){
        ltAlbumTask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idAlbum",idalbumtask3);
                bundle.putString("SDT",SDT);
                intent.putExtra("idAlbum",bundle);
                startActivity(intent);
            }
        });
    }

    public void loadData(){
        addTopSong();
        addArtist();
    }
}