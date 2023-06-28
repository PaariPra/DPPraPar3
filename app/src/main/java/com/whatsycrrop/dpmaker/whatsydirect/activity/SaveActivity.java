package com.whatsycrrop.dpmaker.whatsydirect.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.chetsapp.whatsydirect.R;
import com.chetsapp.whatsydirect.adsclass.ShowNAtivrbannerAds;
import com.chetsapp.whatsydirect.fragmnet.MySaveAdapter;
import com.google.android.material.tabs.TabLayout;

public class SaveActivity extends AppCompatActivity {


  private TabLayout tabLayout;
  private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);


        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        ShowNAtivrbannerAds showNAtivrbannerAds = new ShowNAtivrbannerAds();
        showNAtivrbannerAds.refreshAd(SaveActivity.this, frameLayout, false);


        intDAta();



    }

    private void intDAta() {
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.addTab(tabLayout.newTab().setText("Image"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MySaveAdapter adapter = new MySaveAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
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