package com.whatsycrrop.dpmaker.adsclass

import android.app.Activity
import android.util.Log
import com.whatsycrrop.dpmaker.activity.SplashActivity
import com.whatsycrrop.dpmaker.utiles.TinyDB

class ShowIntertialads {
    fun shaowinr(activity: Activity?, cAllBack: CAllBack) {
        var tinyDB: TinyDB = TinyDB(activity!!.applicationContext);
        var status: Int =tinyDB.getInt("status");
        var count: Int =tinyDB.getInt("count");

        if (status == 1) {

            if (SplashActivity.countvar0 === 0) {
                SplashActivity.countvar0 = 1
                Log.e("TAG", "shaowinr:_____1")


                AdmobgoogleAdsall.getsinterface()
                    .showInterstitial(activity, status) { cAllBack.callbac() }
            } else {
                Log.e("TAG", "shaowinr:_____2222")
                SplashActivity.countvar0++
                if (SplashActivity.countvar0 === count) {
                    SplashActivity.countvar0 = 1
                    Log.e("TAG", "shaowinr:_____333333")
                    AdmobgoogleAdsall.getsinterface()
                        .showInterstitial(activity, status) { cAllBack.callbac() }
                } else {
                    cAllBack.callbac()
                }
            }
        } else {

            cAllBack.callbac()
        }
    }

    interface CAllBack {
        fun callbac()
    }




    fun shaowinr2(activity: Activity?, cAllBack: CAllBack)
    {
        var tinyDB: TinyDB = TinyDB(activity!!.applicationContext);
        var status: Int =tinyDB.getInt("status");


        if (status == 1) {

            tinyDB.putInt("back", 1)
            Log.e("TAG", "shaowinr:_____1")
            SplashActivity.countvar0 = 0;
            AdmobgoogleAdsall.getsinterface()
                .showInterstitial3(activity, status) { cAllBack.callbac() }



        } else {

            cAllBack.callbac()
        }
    }
}