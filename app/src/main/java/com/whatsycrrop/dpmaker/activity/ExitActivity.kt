package com.whatsycrrop.dpmaker.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.adsclass.ShowNAtivrbannerAds
import com.whatsycrrop.dpmaker.utiles.TinyDB
import kotlinx.android.synthetic.main.activity_exit.*

class ExitActivity : AppCompatActivity() {

    private var tinyDB: TinyDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        tinyDB = TinyDB(this@ExitActivity)

        val showNAtivrbannerAds = ShowNAtivrbannerAds()
        showNAtivrbannerAds.refreshAd(this@ExitActivity, frameLayout!!)


        tv_yes.setOnClickListener {
           finishAffinity()
        }


        tv_no.setOnClickListener {

            tinyDB!!.putInt("back", 0)

            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        tinyDB!!.putInt("back", 0)
    }
}