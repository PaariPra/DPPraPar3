package com.whatsycrrop.dpmaker.activity;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsycrrop.dpmaker.R;

import com.whatsycrrop.dpmaker.adsclass.Appopnclas;
import com.whatsycrrop.dpmaker.adsclass.ConsData;
import com.whatsycrrop.dpmaker.adsclass.PhotoApp;
import com.whatsycrrop.dpmaker.databas.DataStatuses;
import com.whatsycrrop.dpmaker.utiles.TinyDB;

import java.util.Date;

public class SplashActivity extends BasedataActivity {

    private TinyDB tinyDB;
    private LottieAnimationView image_view;
    private Application application;
    private Appopnclas appopnclas;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appopnclas = new Appopnclas();
        image_view = findViewById(R.id.image_view);
        application = getApplication();

        image_view.setVisibility(View.VISIBLE);
        image_view.playAnimation();

        initDAta();


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (checkConnection(SplashActivity.this)) {

            onlyload();
            appopnclas.loadAppopen(SplashActivity.this);
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                }
            },2000);


        }


    }


    private void initDAta() {
        tinyDB = new TinyDB(SplashActivity.this);


        tinyDB.putInt("back", 0);

        if (tinyDB.getInt("status") == 0) {

            tinyDB.putInt("status", 2);
            tinyDB.putInt("count", 3);

            tinyDB.putInt("natstatus", 2);
            tinyDB.putInt("allshow", 2);


        }

        tinyDB.putInt("status", 1);
        tinyDB.putInt("count", 3);
        tinyDB.putInt("natstatus", 1);
        tinyDB.putInt("allshow", 1);


        tinyDB.putInt(ConsData.InsDefkey, ConsData.InsDefvalue);


    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }


}