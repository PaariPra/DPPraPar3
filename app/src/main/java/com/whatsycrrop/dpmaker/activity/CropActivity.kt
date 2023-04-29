package com.whatsycrrop.dpmaker.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.utiles.BitmapUtiles
import com.lyrebirdstudio.aspectratiorecyclerviewlib.aspectratio.model.AspectRatio
import com.lyrebirdstudio.croppylib.Croppy
import com.lyrebirdstudio.croppylib.main.CropRequest
import com.lyrebirdstudio.croppylib.main.CroppyTheme
import com.lyrebirdstudio.croppylib.main.StorageType
import com.lyrebirdstudio.croppylib.util.file.FileCreator
import com.lyrebirdstudio.croppylib.util.file.FileOperationRequest


class CropActivity : BasedataActivity() {
    var uri: Uri? = null
    var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)


        uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@CropActivity, uri)
        var w: Int = bitmap!!.getWidth()
        var h: Int = bitmap!!.getHeight()
        val s = Math.max(w / 2048.0f, h / 2048.0f)

        if (s > 1.0f) {
            w /= s.toInt()
            h /= s.toInt()
            bitmap = Bitmap.createScaledBitmap(bitmap!!, w, h, false)
        } else {
            bitmap = bitmap
        }


        startCroppy()

    }

    private fun startCroppy() {

        val externalCropRequest = CropRequest.Auto(
            sourceUri = uri!!,
            requestCode = RC_CROP_IMAGE
        )

        //Saves to cache and return uri
        val cacheCropRequest = CropRequest.Auto(
            sourceUri = uri!!,
            requestCode = RC_CROP_IMAGE,
            storageType = StorageType.CACHE
        )

        val destinationUri =
            FileCreator
                .createFile(FileOperationRequest.createRandom(), applicationContext)
                .toUri()

        Log.e("TAG", "startCroppy: " + destinationUri.toString())

        val manualCropRequest = CropRequest.Manual(
            sourceUri = uri!!,
            destinationUri = destinationUri,
            requestCode = RC_CROP_IMAGE
        )

        val excludeAspectRatiosCropRequest = CropRequest.Manual(
            sourceUri = uri!!,
            destinationUri = destinationUri,
            requestCode = RC_CROP_IMAGE,
            excludedAspectRatios = arrayListOf(AspectRatio.ASPECT_FREE)
        )

        Log.e("TAG", "startCroppy: " + uri)

        val themeCropRequest = CropRequest.Manual(
            sourceUri = uri!!,
            destinationUri = destinationUri,
            requestCode = RC_CROP_IMAGE,
            croppyTheme = CroppyTheme(R.color.purple_200)
        )

        Log.e("TAG", "startCroppy1111: " + uri)
        Log.e("TAG", "startCroppy1111: " + themeCropRequest)

        Croppy.start(this, themeCropRequest)
        Log.e("TAG", "startCroppy111144: " + themeCropRequest)

    }

    companion object {
        private const val RC_CROP_IMAGE = 102

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("TAG", "onActivityResult: "+resultCode )
        if(resultCode==RESULT_OK) {

            if (requestCode == RC_CROP_IMAGE) {
                data?.data?.let {





                    showInterstitial( object : CAllBack {
                        override fun callbac() {

                            startActivity(
                                Intent(
                                    this@CropActivity,
                                    EditActivity::class.java
                                ).putExtra("imageuri", it.toString())


                            )
                            finish()
                        }
                    })





                }
            }
        }
        else
        {
            finish()
        }


    }


}