package com.whatsycrrop.dpmaker.whatsydirect.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.chetsapp.whatsydirect.R;
import com.chetsapp.whatsydirect.adsclass.ShowNAtivrbannerAds;
import com.chetsapp.whatsydirect.fragmnet.MyAdapter;
import com.chetsapp.whatsydirect.fragmnet.TinyDB;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

public class StatusActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);



        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        ShowNAtivrbannerAds showNAtivrbannerAds = new ShowNAtivrbannerAds();
        showNAtivrbannerAds.refreshAd(StatusActivity.this, frameLayout, false);



        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("" + "Video"));
        tabLayout.addTab(tabLayout.newTab().setText("Image"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        finish();
    }





}