package com.chetsapp.whatsydirect.adsclass

import android.app.Activity
import android.util.Log
import com.chetsapp.whatsydirect.activity.SplashActivity
import com.chetsapp.whatsydirect.fragmnet.TinyDB


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
}