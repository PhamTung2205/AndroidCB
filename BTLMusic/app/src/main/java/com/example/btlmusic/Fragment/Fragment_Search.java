package com.example.btlmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btlmusic.Activity.AlbumActivity;
import com.example.btlmusic.Activity.ArtistActivity;
import com.example.btlmusic.Activity.MainActivity;
import com.example.btlmusic.Activity.SongActivity;
import com.example.btlmusic.Activity.SonglistActivity;
import com.example.btlmusic.Manager.AlbumManager;
import com.example.btlmusic.Manager.ArtistManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Album;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Search extends Fragment {
    String SDT;
    MainActivity mainActivity;
    EditText edtFragSearchSong;
    Button btnFragSearchSong;
    ListView lvFragSong;
    SongManager songManager;
    ArtistManager artistManager;
    AlbumManager albumManager;
    ArrayList<Object> listSearch= new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Search newInstance(String param1, String param2) {
        Fragment_Search fragment = new Fragment_Search();
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
        getView();
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        getView(view);
        try {
            songManager = new SongManager(getContext());
            songManager.open();
            albumManager = new AlbumManager(getContext());
            albumManager.open();
            artistManager = new ArtistManager(getContext());
            artistManager.open();

        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(getContext(),"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        mainActivity = (MainActivity) getActivity();
        SDT = mainActivity.SDT;
        clickSelectSong();
        clickItem();
        // Inflate the layout for this fragment
        return view;
    }
    public void getView(View view){
        edtFragSearchSong =view.findViewById(R.id.edt_fragSearchSong);
        btnFragSearchSong = view.findViewById(R.id.btn_fragSearchSong);
        lvFragSong = view.findViewById(R.id.lv_fragSearchSong);

    }
    public void defaultListSearch(){
        List<Song> songList = songManager.selectAllSong();
        List<Artist> artistList = artistManager.selectAllArtist();
        List<Album> albumList = albumManager.selectAllAlbum();

        listSearch.addAll(songList);
        listSearch.addAll(artistList);
        listSearch.addAll(albumList);
        ArrayAdapter<Object> nameSong2 = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,listSearch);
        lvFragSong.setAdapter(nameSong2);
    }
    public void addSearch(){
        if(edtFragSearchSong.getText().toString().trim().isEmpty()){
            listSearch = new ArrayList<>();
            defaultListSearch();
        }
        else {
            listSearch=new ArrayList<>();
            List<Song> songList = songManager.selectSong(edtFragSearchSong.getText().toString().trim());
            List<Artist> artistList = artistManager.selectArtist(edtFragSearchSong.getText().toString().trim());
            List<Album> albumList = albumManager.selectAlbum(edtFragSearchSong.getText().toString().trim());


            listSearch.addAll(songList);
            listSearch.addAll(artistList);
            listSearch.addAll(albumList);
            ArrayAdapter<Object> nameSong2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,listSearch);
            lvFragSong.setAdapter(nameSong2);
        }
    }
    public void clickSelectSong(){
        btnFragSearchSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSearch();
            }
        });
    }

    public void clickItem(){
        lvFragSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectItem = listSearch.get(i);
                if(selectItem instanceof Song){
                    Song song = (Song) selectItem;
                    Intent intent = new Intent(getContext(), SonglistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idSong",song.getId());
                    bundle.putString("SDT",SDT);
                    bundle.putString("Type","Song");
                    intent.putExtra("idSong",bundle);
                    startActivity(intent);
                }
                if(selectItem instanceof Album){
                    Album album = (Album) selectItem;
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idAlbum",album.getId());
                    bundle.putString("SDT",SDT);
                    intent.putExtra("idAlbum",bundle);
                    startActivity(intent);
                }
                if(selectItem instanceof Artist){
                    Artist artist = (Artist) selectItem;
                    Intent intent = new Intent(getContext(), ArtistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idArtist",artist.getId());
                    bundle.putString("SDT",SDT);
                    intent.putExtra("idArtist",bundle);
                    startActivity(intent);
                }
            }
        });
    }
    public void loadData(){
        edtFragSearchSong.setText("");
        edtFragSearchSong.findFocus();
        if(edtFragSearchSong.getText().toString().trim().isEmpty()){
            listSearch = new ArrayList<>();
            defaultListSearch();
        }
    }
}