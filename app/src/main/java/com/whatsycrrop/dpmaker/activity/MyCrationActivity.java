package com.whatsycrrop.dpmaker.activity;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.whatsycrrop.dpmaker.R;
import com.whatsycrrop.dpmaker.adapter.CreationAdapter;
import com.whatsycrrop.dpmaker.adapter.clickintertial;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MyCrationActivity extends BasedataActivity {
    ImageView ic_back2;
    TextView iv_no;
    RecyclerView rv_alldata;

    CreationAdapter myListAdapter;
    private AdView adView;

    ArrayList<File> rm;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ploadallint();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cration);


        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        if (checkConnection(MyCrationActivity.this)) {

            datashonateve(frameLayout);
        }

        ic_back2 = findViewById(R.id.ic_back2);
        rv_alldata = findViewById(R.id.rv_alldata);
        iv_no = findViewById(R.id.iv_no);


        ic_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
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

                    if (pictures[i].getName().equals("Test.png")) {
                        pictures[i].delete();
                    } else {
                        rm.add(pictures[i]);
                    }
                }

                iv_no.setVisibility(View.GONE);
                rv_alldata.setVisibility(View.VISIBLE);

                Collections.reverse(rm);

                myListAdapter = new CreationAdapter(rm, MyCrationActivity.this, new clickintertial() {

                    @Override
                    public void dataclas(File file) {

                        showInterstitial(new CAllBack() {
                            @Override
                            public void callbac() {

                                startActivity(new Intent(MyCrationActivity.this, PreviewActivity.class)
                                        .putExtra("uri", FileProvider.getUriForFile(MyCrationActivity.this, getPackageName() + ".provider", file).toString())
                                        .putExtra("path", file.getAbsolutePath())
                                );


                            }
                        });


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