package com.whatsycrrop.dpmaker.whatsydirect.fragmnet;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chetsapp.whatsydirect.fragmnet.ImageSaveFragment;
import com.chetsapp.whatsydirect.fragmnet.VideoSaveFragment;

public class MySaveAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MySaveAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs  
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                VideoSaveFragment sportFragment = new VideoSaveFragment();
                return sportFragment;

            case 1:



            ImageSaveFragment homeFragment = new ImageSaveFragment();
            return homeFragment;

            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}  