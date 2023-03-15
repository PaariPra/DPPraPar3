package com.whatsycrrop.dpmaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.whatsycrrop.dpmaker.R;
import com.whatsycrrop.dpmaker.adapter.CreationAdapter;
import com.whatsycrrop.dpmaker.adsclass.ShowIntertialads;
import com.whatsycrrop.dpmaker.adsclass.ShowNAtivrbannerAds;
import com.whatsycrrop.dpmaker.interfaceces.selectectposion;
import com.whatsycrrop.dpmaker.utiles.TinyDB;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MyCrationActivity extends AppCompatActivity {
    ImageView ic_back2;
    TextView iv_no;
    RecyclerView rv_alldata;

    CreationAdapter myListAdapter;
    private AdView adView;

    ArrayList<File> rm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cration);


        FrameLayout frameLayout= findViewById(R.id.frameLayout);

        ShowNAtivrbannerAds showNAtivrbannerAds = new ShowNAtivrbannerAds();
        showNAtivrbannerAds.refreshAd(MyCrationActivity.this, frameLayout);



        ic_back2 = findViewById(R.id.ic_back2);
        rv_alldata = findViewById(R.id.rv_alldata);
        iv_no = findViewById(R.id.iv_no);


        ic_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS,
                "WhtssiCropy");


        File[] pictures = file.listFiles();

        if (pictures != null) {

            rm = new ArrayList<File>();


            if (pictures.length > 0) {

                for (int i = 0; i < pictures.length; i++) {

                    if(pictures[i].getName().equals("Test.png"))
                    {
                        pictures[i].delete();
                    }
                    else {
                        rm.add(pictures[i]);
                    }
                }

                iv_no.setVisibility(View.GONE);
                rv_alldata.setVisibility(View.VISIBLE);

                Collections.reverse(rm);

                myListAdapter = new CreationAdapter(rm, MyCrationActivity.this, new selectectposion() {
                    @Override
                    public void potinodate(int postion) {


                    }
                });
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MyCrationActivity.this, 2);
                rv_alldata.setLayoutManager(gridLayoutManager);
                rv_alldata.setHasFixedSize(true);
                rv_alldata.setAdapter(myListAdapter);


            } else {
                iv_no.setVisibility(View.VISIBLE);
                rv_alldata.setVisibility(View.GONE);
            }
        } else {
            iv_no.setVisibility(View.VISIBLE);
            rv_alldata.setVisibility(View.GONE);

        }


    }
}