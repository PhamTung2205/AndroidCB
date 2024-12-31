package com.example.btlmusic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.btlmusic.Adaptrer.ViewPageAdapter;
import com.example.btlmusic.Fragment.Fragment_Account;
import com.example.btlmusic.Fragment.Fragment_Favorite;
import com.example.btlmusic.Fragment.Fragment_Home;
import com.example.btlmusic.Fragment.Fragment_Search;
import com.example.btlmusic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    ViewPageAdapter adapter;
    Fragment_Home fragment_home;
    Fragment_Search fragment_search;
    Fragment_Favorite fragment_favorite;
    Fragment_Account fragment_account;
    public String SDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getValues();
        getView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clickMenu();
        showPageView();

    }
    private void getValues(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("User");
        SDT = bundle.getString("SDT");

    }

    private void getView() {
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.menu_navigation);
        adapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
    }

    private void clickMenu() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_search).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_favorite).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void showPageView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_home){
                    viewPager.setCurrentItem(0);
                    fragment_home = (Fragment_Home) viewPager.getAdapter().instantiateItem(viewPager,0);
                    fragment_home.loadData();
                }
                if(item.getItemId()==R.id.menu_search){
                    viewPager.setCurrentItem(1);
                    fragment_search = (Fragment_Search) viewPager.getAdapter().instantiateItem(viewPager,1);
                    fragment_search.loadData();
                }
                if(item.getItemId()==R.id.menu_favorite){
                    viewPager.setCurrentItem(2);
                    fragment_favorite = (Fragment_Favorite) viewPager.getAdapter().instantiateItem(viewPager,2);
                    fragment_favorite.loadData();
                }
                if(item.getItemId()==R.id.menu_account){
                    viewPager.setCurrentItem(3);
                    fragment_account = (Fragment_Account) viewPager.getAdapter().instantiateItem(viewPager,3);
                    fragment_account.loadData();
                }

                return true;
            }
        });
    }



}