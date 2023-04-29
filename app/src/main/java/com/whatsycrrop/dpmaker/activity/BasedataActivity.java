package com.whatsycrrop.dpmaker.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.whatsycrrop.dpmaker.R;
import com.whatsycrrop.dpmaker.adsclass.ConsData;
import com.whatsycrrop.dpmaker.adsclass.PhotoApp;
import com.whatsycrrop.dpmaker.utiles.TinyDB;

import java.util.Arrays;
import java.util.Date;


public class BasedataActivity extends AppCompatActivity {
    static InterstitialAd interstitialAd1;
    String AD_UNIT_ID;
    public static final String LOG_TAG = "AppOpenAdManager";

    private long loadTime = 0;
    static NativeAd nativeAd1;
    TinyDB tinyDB;
    AdView adView;

    public static boolean data = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basedata);
        tinyDB = new TinyDB(this);


    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }

    }
    //todo banner

    public void banneraddload(FrameLayout adview) {

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adview.addView(adView);

        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                        .build()
        );

        adView.loadAd(new AdRequest.Builder().build());

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {

            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdOpened() {

            }
        });

    }


    public void ploadallint() {

        incremtconter();
        if (checkConnection(this)) {
            if (tinyDB.getInt(ConsData.PlusDefkey) >= tinyDB.getInt(ConsData.InsDefkey)) {
                loadInsertial();

            }
        }

    }


    public void incremtconter() {
        tinyDB.putInt(ConsData.PlusDefkey, tinyDB.getInt(ConsData.PlusDefkey) + 1);

        /*  Toast.makeText(this, "" + tinyDB.getInt(ConsData.PlusDefkey) + "/" + tinyDB.getInt(ConsData.InsDefkey), Toast.LENGTH_SHORT).show();*/


    }


    public void resentcounter() {

        TinyDB tinyDB = new TinyDB(this);
        tinyDB.putInt(ConsData.PlusDefkey, 0);
    }


    public void loadInsertial() {
        AD_UNIT_ID = getResources().getString(R.string.inter_ad_unit_id);
        Log.e("TAG", "LOdoagoooddddintee if avvv1111,,,,,,,,,,,,,,,," + interstitialAd1);

        if (data = true) {
            data = false;
            if (interstitialAd1 == null) {

                Log.e("TAG", "LOdoagoooddddintee if avvv,,,,,,,,,,,,,,,," + interstitialAd1);
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(
                        getApplicationContext(),
                        AD_UNIT_ID,
                        adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                interstitialAd1 = interstitialAd;
                                data = true;

                                Log.e("TAG", "LOdoagoooddddintee if avvv1111,,,,,,,,,,,,,,,,onAdLoaded" + interstitialAd1);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                                Log.e("TAG", "LOdoagoooddddintee if avvv1111,,,,,,,,,,,,,,,,onAdFailedToLoad" + interstitialAd1);
                                interstitialAd1 = null;
                                data = true;
                            }

                        });
            }
        }

    }


    public void showInterstitial(CAllBack cAllBack) {

        if (tinyDB.getInt(ConsData.PlusDefkey) > tinyDB.getInt(ConsData.InsDefkey) && checkConnection(this)) {
            if (interstitialAd1 != null) {
                Log.e("TAG", "LOdoagoooddddintee if avvv1111,,,,,,,,,,,,,,,,showInterstitial");
                showgoosins(cAllBack);
                interstitialAd1.show(this);


            } else {
                ploadallint();
                cAllBack.callbac();

            }
        } else {
            ploadallint();
            cAllBack.callbac();
        }


    }


    public interface CAllBack {
        void callbac();
    }


    private void showgoosins(CAllBack cAllBack) {

        interstitialAd1.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialAd1 = null;
                        resentcounter();
                        cAllBack.callbac();

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        interstitialAd1 = null;
                        Log.e("TAG", "The ad failed to show.");
                        resentcounter();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        interstitialAd1 = null;
                        resentcounter();
                        Log.e("TAG", "The ad was shown.");
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    //TODO for native


    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }


        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }


        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.GONE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.GONE);
        }


        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }


        adView.setNativeAd(nativeAd);


        VideoController vc = nativeAd.getMediaContent().getVideoController();


        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {


                    super.onVideoEnd();
                }
            });
        } else {

        }
    }


    public void destronative() {
        if (nativeAd1 != null) {
            nativeAd1.destroy();
            nativeAd1 = null;
        }

    }

    public void datashonateve(FrameLayout frameLayout) {

        if (checkConnection(this)) {
            if (nativeAd1 != null) {

                NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateNativeAdView(nativeAd1, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);


            } else {
                nativeAd1 = null;
                refreshAd(frameLayout);
            }
        }


    }

    public void onlyload() {

        if (checkConnection(this)) {

            AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.native_ad_unit_id));

            builder.forNativeAd(
                    new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {

                            boolean isDestroyed = false;
                            if (nativeAd1 != null) {
                                nativeAd1.destroy();
                            }
                            nativeAd1 = nativeAd;


                        }
                    });

            VideoOptions videoOptions =
                    new VideoOptions.Builder().setStartMuted(true).build();

            NativeAdOptions adOptions =
                    new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

            builder.withNativeAdOptions(adOptions);

            AdLoader adLoader =
                    builder
                            .withAdListener(
                                    new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(LoadAdError loadAdError) {


                                        }
                                    })
                            .build();

            adLoader.loadAd(new AdRequest.Builder().build());


        }


    }

    public void refreshAd(FrameLayout frameLayout) {


        if (checkConnection(this)) {

            AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.native_ad_unit_id));

            builder.forNativeAd(
                    new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {

                            boolean isDestroyed = false;
                            if (nativeAd1 != null) {
                                nativeAd1.destroy();
                            }
                            nativeAd1 = nativeAd;

                            NativeAdView adView =
                                    (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                            populateNativeAdView(nativeAd, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);


                        }
                    });

            VideoOptions videoOptions =
                    new VideoOptions.Builder().setStartMuted(true).build();

            NativeAdOptions adOptions =
                    new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

            builder.withNativeAdOptions(adOptions);

            AdLoader adLoader =
                    builder
                            .withAdListener(
                                    new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(LoadAdError loadAdError) {


                                        }
                                    })
                            .build();

            adLoader.loadAd(new AdRequest.Builder().build());


        }





    }

    public static boolean checkConnection(Activity activity) {
        final ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {


            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                return true;
            }
        }
        return false;
    }

}