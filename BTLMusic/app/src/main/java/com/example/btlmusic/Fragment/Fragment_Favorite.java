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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlmusic.Activity.MainActivity;
import com.example.btlmusic.Activity.SongActivity;
import com.example.btlmusic.Activity.SonglistActivity;
import com.example.btlmusic.Manager.AccountManager;
import com.example.btlmusic.Manager.FavoriteManager;
import com.example.btlmusic.Manager.SongManager;
import com.example.btlmusic.Opject.Account;
import com.example.btlmusic.Opject.Album;
import com.example.btlmusic.Opject.Artist;
import com.example.btlmusic.Opject.Favorite;
import com.example.btlmusic.Opject.Song;
import com.example.btlmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Favorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Favorite extends Fragment {

    TextView txtNameAcc;
    ListView lvNameSong;
    AccountManager accountManager;
    SongManager songManager;
    Account account;
    FavoriteManager favoriteManager;
    String SDT;
    MainActivity mainActivity;
    ArrayList<Object> listSong = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Favorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Favorite newInstance(String param1, String param2) {
        Fragment_Favorite fragment = new Fragment_Favorite();
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
        View view =inflater.inflate(R.layout.fragment_favorite, container, false);
        getView(view);
        try {
            accountManager = new AccountManager(getContext());
            accountManager.open();
            songManager = new SongManager(getContext());
            songManager.open();
            favoriteManager = new FavoriteManager(getContext());
            favoriteManager.open();
        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(getContext(),"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        mainActivity = (MainActivity) getActivity();
        SDT = mainActivity.SDT;
        account = selectAccount(SDT);
        getNameSong();
        clickItem();
        // Inflate the layout for this fragment
        return view;
    }
    public void getView(View view){
        txtNameAcc = view.findViewById(R.id.fragfavorite_txtNameAcc);
        lvNameSong = view.findViewById(R.id.fragfavorite_lsSong);
    }

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

    public void getNameSong(){
        txtNameAcc.setText(account.getName());
        List<Song> songList = songManager.selectAllSong();
        List<Favorite> favoriteList = favoriteManager.selectAllFavorite();
        List<Favorite> favorites = new ArrayList<>();
        for(Favorite item :favoriteList){
            if(item.getId_account()==account.getId()){
                favorites.add(item);
            }
        }
        List<Song> songs = new ArrayList<>();
        for(Favorite item : favorites){
            for (Song item1 : songList){
                if(item.getId_song()==item1.getId()){
                    songs.add(item1);
                }
            }
        }
        listSong.addAll(songs);
        ArrayAdapter<Object> nameSong = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,listSong);
        lvNameSong.setAdapter(nameSong);
    }
    public void clickItem(){
        lvNameSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectItem = listSong.get(i);
                if(selectItem instanceof Song){
                    Song song = (Song) selectItem;
                    Intent intent = new Intent(getContext(), SonglistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idSong",song.getId());
                    bundle.putString("SDT",account.getPhone());
                    bundle.putString("Type","Favorite");
                    intent.putExtra("idSong",bundle);
                    startActivity(intent);
                }
            }
        });
    }
    public void loadData(){}
}