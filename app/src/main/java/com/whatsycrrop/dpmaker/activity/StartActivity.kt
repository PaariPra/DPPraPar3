package com.whatsycrrop.dpmaker.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.widget.AdapterViewFlipper
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lyrebirdstudio.croppylib.main.FileCompressor
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.adapter.AdapterViewFlipperAdapter
import com.whatsycrrop.dpmaker.adapter.appclick
import com.whatsycrrop.dpmaker.utiles.BitmapUtiles
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import pub.devrel.easypermissions.EasyPermissions.RationaleCallbacks
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class StartActivity : BasedataActivity(), PermissionCallbacks, RationaleCallbacks {
    var selectedImagePath: String? = null
    private val CHILD_DIR = "images"

    var myBitmap: Bitmap? = null
    var mPhotoFile:File?= null
    var mCompressor: FileCompressor? = null


    private var LOCATION_AND_CONTACTS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA)


    var cl_start:ConstraintLayout?=null
    var cl_privi3:ConstraintLayout?=null
    var frameLayout:FrameLayout?=null
    var iv_image:ImageView?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        if(Build.VERSION.SDK_INT>=33)
        {
            Log.e("TAG", "onCreate: uoversio"+Build.VERSION.SDK_INT )
            LOCATION_AND_CONTACTS= arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.CAMERA
            )

        }
        else
        {


            LOCATION_AND_CONTACTS=      arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }

        cl_start=findViewById(R.id.cl_start);
        frameLayout=findViewById(R.id.frameLayout);
        cl_privi3=findViewById(R.id.cl_privi3);
        iv_image=findViewById(R.id.iv_image);




        mCompressor =  FileCompressor(this);




        cl_start!!.setOnClickListener {
            ploadallint();
            locationAndContactsTask()

        }



        cl_privi3!!.setOnClickListener {


            showInterstitial( object : CAllBack {
                override fun callbac() {

                    startActivity(
                        Intent(this@StartActivity, MyCrationActivity::class.java)
                            .putExtra("type", "one")
                    )

                }
            })

        }



      setmoreapps()


        if (checkConnection(this@StartActivity)) {
            datashonateve(frameLayout!!)
        }


    }

    private fun setmoreapps() {
        var languageAdapterViewFlipper: AdapterViewFlipper? = null
        var languageImageArray = intArrayOf(
            R.drawable.app_logo_ro,
            R.drawable.ic_logo3,
            R.drawable.ic_logo2)



        var programmingLanguages = arrayOf(
            "Photo Editor: BackgroundRemove",
            "Whatzapp Status Downloader",
            " Collage Maker: Photo Editor")



        var applinkphoto = arrayOf(
            "market://details?id=com.chetssholic.removebackgeround",
             "market://details?id=com.chetsapp.whatsydirect",
            "market://details?id=com.chettapps.photocollagemaker")




        languageAdapterViewFlipper = findViewById(R.id.idAVFlipper)

        val adapterViewFlipperAdapter = AdapterViewFlipperAdapter(
            applicationContext, languageImageArray, programmingLanguages,applinkphoto , object : appclick
            {
                override fun appclic(pa:String) {
                    ploadallint()

                    val uri = Uri.parse(pa)
                    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(myAppLinkToMarket)
                    }
                    catch (e:Exception)
                    {
                        Toast.makeText(this@StartActivity, " unable to find market app", Toast.LENGTH_LONG).show()
                    }
                }

            }

        )

        languageAdapterViewFlipper.adapter = adapterViewFlipperAdapter
        languageAdapterViewFlipper.flipInterval = 2500
        languageAdapterViewFlipper.isAutoStart = true

    }


    private fun getImage(requestCode: Int, type: String) {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, requestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("TAG", "onActivityResult: __!11ewade"+data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)
        }



        if (resultCode == Activity.RESULT_OK) {

            Log.e("TAG", "onActivityResult: __!11" + requestCode)
            data?.data?.also {


                if (requestCode == 101) {
                    Glide.with(this@StartActivity)
                        .load(it)
                        .listener(object : RequestListener<Drawable?> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                isFirstResource: Boolean
                            ): Boolean {

                                return false

                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable?>?,
                                dataSource: com.bumptech.glide.load.DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {

                                val bitap: Bitmap = BitmapUtiles.getBitmap(resource)
                                Log.e("TAG", "onResourceReady: " + bitap)

                                val uri: Uri = BitmapUtiles.saveToCacheAndGetUri(
                                    bitap,
                                    "phortedot",
                                    this@StartActivity
                                );
                                Log.e("TAG", "onResourceReady: " + uri)




                                showInterstitial(object : CAllBack {
                                    override fun callbac() {
                                        ploadallint()
                                        startActivity(
                                            Intent(
                                                this@StartActivity,
                                                CropActivity::class.java
                                            ).putExtra("imageuri", uri.toString())


                                        )

                                    }
                                })




                                Glide.TRIM_MEMORY_COMPLETE
//                                Glide.with(this@StartActivity)
//                                    .load(null).into(iv_image)


                                /*finish()*/
                                return true;

                            }


                        })
                        .into(iv_image!!)


                }


            }



            if (requestCode == 102) {


                val bitmap: Bitmap
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                } catch (e: IOException) {
                    e.printStackTrace()


                }
                val photoURI = FileProvider.getUriForFile(
                    this, getPackageName() + ".provider",
                    mPhotoFile!!
                )



                showInterstitial(object : CAllBack {
                    override fun callbac() {
                        ploadallint()

                        startActivity(
                            Intent(
                                this@StartActivity,
                                CropActivity::class.java
                            ).putExtra("imageuri", photoURI.toString())


                        )

                    }
                })





            }


        }

    }

    fun getPickImageResultUri(data: Intent?): Uri? {
        Log.e("TAG", "getPickImageResultUri: 1111" )
        var isCamera = true
        if (data != null) {
            Log.e("TAG", "getPickImageResultUri: 222" +data.action)
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }

        Log.e("TAG", "getPickImageResultUri: 33" )

        var file = getImageFile();
        Log.e("TAG", "getPickImageResultUri: 33"+file )
        var uri = FileProvider.getUriForFile(
            this@StartActivity,
            getPackageName() + ".provider",
            file!!
        )

        return if (isCamera) uri else data!!.data
    }



    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap? {
        val ei = ExifInterface(selectedImage.path!!)
        val orientation: Int =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
            else -> img
        }
    }


    private fun hasLocationAndContactsPermissions1(): Boolean {
        return EasyPermissions.hasPermissions(this, *LOCATION_AND_CONTACTS)
    }


    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    fun locationAndContactsTask() {
        if (hasLocationAndContactsPermissions1()) {

            val dialog = Dialog(this@StartActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.select_dialog)

            var cl_galler: ConstraintLayout = dialog.findViewById(R.id.cl_galler);
            var iv_close: ImageView = dialog.findViewById(R.id.iv_close);

            cl_galler.setOnClickListener {

            }

            var cl_camera: ConstraintLayout = dialog.findViewById(R.id.cl_camera)


            cl_galler.setOnClickListener {
                ploadallint();
                getImage(101, "drip")
                dialog.dismiss()
            }


            cl_camera.setOnClickListener {

                ploadallint();
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    var photoFile: File? = null
                    try {
                        photoFile = getImageFile()
                    } catch (ex: IOException) {
                        ex.printStackTrace()

                    }
                    if (photoFile != null) {
                        val photoURI = FileProvider.getUriForFile(
                            this, getPackageName() + ".provider",
                            photoFile
                        )
                        mPhotoFile = photoFile
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, 102)
                    }


                    dialog.dismiss()


                }


            }


            iv_close.setOnClickListener { dialog.dismiss() }
            dialog.show()




        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location_contacts),
                RC_LOCATION_CONTACTS_PERM,
                *LOCATION_AND_CONTACTS
            )
        }


    }


    companion object {
        private const val TAG = "MainActivity"
        private  var  countvar0: Int?=0;




        private const val RC_CAMERA_PERM = 123
        private const val RC_LOCATION_CONTACTS_PERM = 124
    }


    @Throws(IOException::class)
    private fun getImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "jpg_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir!!.mkdirs()
        val imageFile = File.createTempFile(imageName, ".jpg", storageDir)


        return imageFile
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size)
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }


    override fun onRationaleAccepted(requestCode: Int) {
        Log.d("TAG", "onRationaleAccepted:$requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d("TAG", "onRationaleDenied:$requestCode")
    }


    override fun onBackPressed()
    {

        showInterstitial(object : CAllBack {
           override fun callbac() {

               startActivity(Intent(this@StartActivity, ExitActivity::class.java))

           }
        })
    }




}