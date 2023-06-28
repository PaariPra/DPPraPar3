package com.whatsycrrop.dpmaker.whatsydirect;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Whatsy  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e("TAG", "onInitializationComplete: " );

            }
        });

    }
}
