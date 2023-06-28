package com.whatsycrrop.dpmaker.whatsydirect.adsclass;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chetsapp.whatsydirect.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class AdmobgoogleAdsall {
 public  static  AdmobgoogleAdsall admobgoogleAds;
    private InterstitialAd interstitialAd1;
    CAllBack cAllBack;
    private static  String AD_UNIT_ID ;


    public void loadAd(Activity  activity, int status) {

        if(status==1) {
            Log.e("TAG", "onAdLoaded");
            AD_UNIT_ID = activity.getResources().getString(R.string.inter_ad_unit_id);

            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(
                    activity.getApplicationContext(),
                    AD_UNIT_ID,
                    adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            interstitialAd1 = interstitialAd;
                          Log.e("TAG", "onAdLoaded");
//                            Toast.makeText(activity, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                            interstitialAd.setFullScreenContentCallback(
                                    new FullScreenContentCallback() {
                                        @Override
                                        public void onAdDismissedFullScreenContent() {

                                            interstitialAd1 = null;
                                            cAllBack.callbac();
                                            loadAd(activity,status );


                                        }

                                        @Override
                                        public void onAdFailedToShowFullScreenContent(AdError adError) {

                                            interstitialAd1 = null;
                                            Log.e("TAG", "The ad failed to show.");
                                        }

                                        @Override
                                        public void onAdShowedFullScreenContent() {

                                            Log.e("TAG", "The ad was shown.");
                                        }
                                    });
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            Log.i("TAG", loadAdError.getMessage());
                            interstitialAd1 = null;


                        }
                    });
        }
    }


    public void   showInterstitial(Activity activity, int statuss, CAllBack cAllBack1) {
         cAllBack=cAllBack1;
        if (interstitialAd1 != null) {
            Log.e("TAG", "showInterstitial:_________ " );
            interstitialAd1.show(activity);
        } else {
            loadAd(activity,statuss);
            cAllBack.callbac();

        }

    }


    public  interface  CAllBack
    {
        void  callbac();
    }

    public  static AdmobgoogleAdsall getsinterface()
    {
     if(admobgoogleAds==null) {
         admobgoogleAds = new AdmobgoogleAdsall();
     }
        return  admobgoogleAds;

    }





}
