package com.whatsycrrop.dpmaker.activity

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.whatsycrrop.dpmaker.BuildConfig
import com.whatsycrrop.dpmaker.R
import java.util.*


class PreviewActivity : BasedataActivity() {
    var uri: Uri? = null


    var ic_back2: ImageView? = null
    var iv_effect2: ImageView? = null
    var iv_more: ImageView? = null
    var iv_share: ImageView? = null
    var cl_start: ConstraintLayout? = null
    var cl_privi: ConstraintLayout? = null

    var bitmap: Bitmap? = null

    override fun onBackPressed() {


        showInterstitial {
            super.onBackPressed()

        }



    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        ic_back2 = findViewById(R.id.ic_back2);
        iv_effect2 = findViewById(R.id.iv_effect2);
        iv_share = findViewById(R.id.iv_share);
        cl_start = findViewById(R.id.cl_start);
        cl_privi = findViewById(R.id.cl_privi);
        iv_more = findViewById(R.id.iv_more);



        uri = Uri.parse(intent.extras!!.getString("uri"))

        ic_back2!!.setOnClickListener {



            onBackPressed();




        }







        iv_effect2!!.setImageURI(uri)
        iv_share!!.setOnClickListener {
            ploadallint()
            funktionTeilen(uri);


        }
        cl_start!!.setOnClickListener {
            ploadallint()
            sharewhats3(uri!!)

        }

        cl_privi!!.setOnClickListener {
            ploadallint()
            sharewhats(uri!!);

        }

        iv_more!!.setOnClickListener {

            ploadallint()
            val dialog = Dialog(this@PreviewActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.app_bar)
            var cl_share: ConstraintLayout = dialog.findViewById(R.id.cl_share);
            var cl_rate: ConstraintLayout = dialog.findViewById(R.id.cl_rate);

            cl_share.setOnClickListener {
                ploadallint()
                dialog.dismiss()
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "WhatsyCrop")
                    var shareMessage = "\nLet me recommend you this application\n\n"
                    shareMessage =
                        """
                        ${
                            shareMessage
                        }https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}


                        """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: java.lang.Exception) {
                    //e.toString();
                }

            }
            cl_rate.setOnClickListener {
                ploadallint()
                dialog.dismiss()


                val uri = Uri.parse("market://details?id=$packageName")
                val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(myAppLinkToMarket)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show()
                }


            }


            dialog.show()

        }


    }


    fun funktionTeilen(Datei: Uri?) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "*/*"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(Intent.EXTRA_SUBJECT, "")
                putExtra(Intent.EXTRA_TEXT, "")
                putExtra(Intent.EXTRA_STREAM, Datei)
            }
            startActivity(shareIntent)
        } catch (e: Exception) {


        }


    }


    fun sharewhats(Datei: Uri) {
        try {


            val intent5 = Intent("android.intent.action.SEND")
            intent5.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent5.setPackage("com.whatsapp")
            intent5.type = "image/*"
            intent5.putExtra("android.intent.extra.STREAM", Datei)
            startActivity(Intent.createChooser(intent5, "Share via"))



        } catch (e: Exception) {
            try {
                val intent5 = Intent("android.intent.action.SEND")
                intent5.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent5.setPackage("com.whatsapp.w4b")
                intent5.type = "image/*"
                intent5.putExtra("android.intent.extra.STREAM", Datei)
                startActivity(Intent.createChooser(intent5, "Share via"))
            } catch (e: Exception) {

            }


        }

    }

    fun sharewhats3(Datei: Uri) {
        try {

            val intent3 = Intent("android.intent.action.ATTACH_DATA")
            intent3.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent3.setPackage("com.whatsapp")
            intent3.setDataAndType(Datei, "*/*")
            intent3.putExtra("android.intent.extra.TEXT", "");
            startActivity(intent3)
        } catch (e: Exception) {

            try {
                val intent3 = Intent("android.intent.action.ATTACH_DATA")
                intent3.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent3.setPackage("com.whatsapp.w4b")
                intent3.setDataAndType(Datei, "*/*")
                intent3.putExtra("android.intent.extra.TEXT", "");
                startActivity(intent3)

            } catch (e: Exception) {


            }


        }


    }


}