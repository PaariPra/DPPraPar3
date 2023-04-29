package com.whatsycrrop.dpmaker.adsclass;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.whatsycrrop.dpmaker.R;
import com.whatsycrrop.dpmaker.activity.SplashActivity;
import com.whatsycrrop.dpmaker.activity.StartActivity;


import java.util.Date;

public class Appopnclas {
    private boolean isAdAvailable() {
        return appOpenAd != null;
    }
    //todo openads
    public AppOpenAd appOpenAd = null;

    public void showAdIfAvailable(Activity activity,
            @NonNull SplashActivity.OnShowAdCompleteListener onShowAdCompleteListener) {


        if (appOpenAd != null) {
            appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {

                        @Override
                        public void onAdDismissedFullScreenContent() {

                            appOpenAd = null;
                            onShowAdCompleteListener.onShowAdComplete();

                        }


                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            appOpenAd = null;
                            onShowAdCompleteListener.onShowAdComplete();

                        }


                        @Override
                        public void onAdShowedFullScreenContent() {


                        }
                    });


            appOpenAd.show(activity);
        } else {
            onShowAdCompleteListener.onShowAdComplete();


        }


    }



    public void loadAppopen(Activity activity)
    {

        appOpenAd = null;
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                activity,
                activity. getResources().getString(R.string.open_ad_unit_id),
                request,
                new AppOpenAd.AppOpenAdLoadCallback() {

                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        appOpenAd = ad;




                            showAdIfAvailable(activity, new SplashActivity.OnShowAdCompleteListener() {
                                @Override
                                public void onShowAdComplete() {
                                    activity.startActivity(new Intent(activity, StartActivity.class));

                                }
                            });





                        }




                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        appOpenAd = null;

                        activity.startActivity(new Intent(activity, StartActivity.class));


                    }
                });
    }
}
