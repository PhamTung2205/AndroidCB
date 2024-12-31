package com.example.btlmusic.Adaptrer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btlmusic.Fragment.Fragment_Account;
import com.example.btlmusic.Fragment.Fragment_Favorite;
import com.example.btlmusic.Fragment.Fragment_Home;
import com.example.btlmusic.Fragment.Fragment_Search;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_Home();
            case 1:
                return new Fragment_Search();
            case 2:
                return new Fragment_Favorite();
            case 3:
                return new Fragment_Account();
            default:
                return new Fragment_Home();
        }
    }


    @Override
    public int getCount() {
        return 4;
    }
}
