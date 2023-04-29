package com.whatsycrrop.dpmaker.activity

import android.os.Bundle
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.utiles.TinyDB
import kotlinx.android.synthetic.main.activity_exit.*

class ExitActivity : BasedataActivity() {

    private var tinyDB: TinyDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        tinyDB = TinyDB(this@ExitActivity)

        if (checkConnection(this@ExitActivity)) {
            datashonateve(frameLayout!!)
        }


        tv_yes.setOnClickListener {
            destronative()
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