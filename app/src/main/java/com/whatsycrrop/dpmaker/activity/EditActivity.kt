package com.whatsycrrop.dpmaker.activity

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.OpenableColumns
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divyanshu.colorseekbar.ColorSeekBar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.adapter.MyListAdapter
import com.whatsycrrop.dpmaker.adsclass.ShowIntertialads
import com.whatsycrrop.dpmaker.filter.ThumbnailCallback
import com.whatsycrrop.dpmaker.filter.ThumbnailItem
import com.whatsycrrop.dpmaker.filter.ThumbnailsAdapter
import com.whatsycrrop.dpmaker.filter.ThumbnailsManager
import com.whatsycrrop.dpmaker.interfaceces.selectectposion
import com.whatsycrrop.dpmaker.photoeditor.EditImageActivity
import com.whatsycrrop.dpmaker.utiles.BitmapUtiles
import com.whatsycrrop.dpmaker.utiles.TinyDB
import com.zomato.photofilters.SampleFilters
import com.zomato.photofilters.imageprocessors.Filter
//import kotlinx.android.synthetic.main.activity_edit.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EditActivity : AppCompatActivity(), ThumbnailCallback {
    var uri: Uri? = null
    var bitmap: Bitmap? = null
    var ad_view: AdView?=null;

    var d = 0f
    var newRot = 0f
    private var isZoomAndRotate = false
    private var isOutSide = false
    private val NONE = 0
    private val DRAG = 1
    private val ZOOM = 2
    private var mode = NONE
    private val start: PointF = PointF()
    private val mid: PointF = PointF()
    var oldDist = 1f
    private var xCoOrdinate = 0f
    private var yCoOrdinate: Float = 0f

    private var iv_effect:ImageView?=null
    private var sek_blure:SeekBar?=null
    private var imageview_id:ImageView?=null
    private var ic_back:ImageView?=null


    private var imageView6:ImageView?=null
    private var imageView7:ImageView?=null
    private var imageView8:ImageView?=null
    private var imageView9:ImageView?=null
    private var imageView10:ImageView?=null
    private var cl_showbg:ConstraintLayout?=null
    private var iv_flp1:ImageView?=null
    private var iv_flp2:ImageView?=null
    private var iv_save:ImageView?=null
    private var iv_fpain:ImageView?=null


    private var RLMain:RelativeLayout?=null


    private var cl_bg:RecyclerView?=null
    private var cl_bg2:RecyclerView?=null
    private var menuLayout:RecyclerView?=null






    private var color_seek_bar:ColorSeekBar?=null
    private var sek_rotation:SeekBar?=null


    companion object {
        init {
            System.loadLibrary("NativeImageProcessor")
        }
    }

    var lastEvent: FloatArray? = null



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK)
        {
            if(requestCode==90)
            {




                uri = Uri.parse(data!!.extras!!.getString("uri"))

                bitmap = BitmapUtiles.decodeUriToBitmap(this@EditActivity, uri)

                var  path = data!!.extras!!.getString("uri")

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

                var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
                var bitmap2: Bitmap? = blur(bmp2, 12f)
                iv_effect!!.setImageBitmap(bitmap2)
                sek_blure!!.progress = 12;

                imageview_id!!.setImageBitmap(bitmap)









            }

        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)



        ad_view=findViewById(R.id.ad_view);
        iv_effect=findViewById(R.id.iv_effect);
        sek_blure=findViewById(R.id.sek_blure);
        ic_back=findViewById(R.id.ic_back);
        imageview_id=findViewById(R.id.imageview_id);
        color_seek_bar=findViewById(R.id.color_seek_bar);
        sek_rotation=findViewById(R.id.sek_rotation);
        iv_flp1=findViewById(R.id.iv_flp1);
        iv_flp2=findViewById(R.id.iv_flp2);
        iv_save=findViewById(R.id.iv_save);
        RLMain=findViewById(R.id.RLMain);


        cl_bg=findViewById(R.id.cl_bg);
        cl_bg2=findViewById(R.id.cl_bg2);


        menuLayout=findViewById(R.id.menuLayout);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);
        imageView10=findViewById(R.id.imageView10);
        cl_showbg=findViewById(R.id.cl_showbg);
        iv_fpain=findViewById(R.id.iv_fpain);



        iv_fpain!!.setOnClickListener {
            val showIntertialads = ShowIntertialads()
            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {


                    val intent = Intent(this@EditActivity, EditImageActivity::class.java)
                    intent.putExtra("uri",  uri.toString())
                    startActivityForResult(intent, 90)





                }
            })




        }





   Daabaner();



        uri = Uri.parse(intent.extras!!.getString("imageuri"))
        bitmap = BitmapUtiles.decodeUriToBitmap(this@EditActivity, uri)
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

        var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
        var bitmap2: Bitmap? = blur(bmp2, 12f)
        iv_effect!!.setImageBitmap(bitmap2)
        sek_blure!!.progress = 12;




        imageview_id!!.setOnTouchListener(View.OnTouchListener { v, event ->
            val view = v as ImageView
            view.bringToFront()
            viewTransformation(view, event)
            true
        })

        imageview_id!!.setImageBitmap(bitmap)

        ic_back!!.setOnClickListener {
            finish()
        }




        imageView9!!.setOnClickListener {
            color_seek_bar!!.visibility = View.VISIBLE

            cl_bg!!.visibility = View.GONE
            cl_bg2!!.visibility = View.GONE

            sek_blure!!.visibility = View.GONE
            sek_rotation!!.visibility = View.GONE
            menuLayout!!.visibility = View.GONE
        }



        cl_showbg!!.setOnClickListener {

            color_seek_bar!!.visibility = View.GONE
            sek_rotation!!.visibility = View.GONE
            sek_blure!!.visibility = View.GONE
            cl_bg!!.visibility = View.GONE
            cl_bg2!!.visibility = View.VISIBLE
            menuLayout!!.visibility = View.GONE
        }





        imageView6!!.setOnClickListener {

            color_seek_bar!!.visibility = View.GONE
            sek_rotation!!.visibility = View.GONE
            sek_blure!!.visibility = View.GONE
            cl_bg!!.visibility = View.VISIBLE
            cl_bg2!!.visibility = View.GONE
            menuLayout!!.visibility = View.GONE


        }

        imageView8!!.setOnClickListener {
            color_seek_bar!!.visibility = View.GONE
            sek_rotation!!.visibility = View.VISIBLE
            sek_blure!!.visibility = View.GONE
            cl_bg!!.visibility = View.GONE
            cl_bg2!!.visibility = View.GONE
            menuLayout!!.visibility = View.GONE

        }


        imageView7!!.setOnClickListener {
            color_seek_bar!!.visibility = View.GONE
            sek_rotation!!.visibility = View.GONE
            cl_bg!!.visibility = View.GONE
            cl_bg2!!.visibility = View.GONE
            sek_blure!!.visibility = View.VISIBLE
            menuLayout!!.visibility = View.GONE


            var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
            var bitmap2: Bitmap? = blur(bmp2, 12f)
            iv_effect!!.setImageBitmap(bitmap2)

            sek_blure!!.progress = 12;


        }

        imageView10!!.setOnClickListener {
            menuLayout!!.visibility = View.VISIBLE
            color_seek_bar!!.visibility = View.GONE
            sek_rotation!!.visibility = View.GONE
            cl_bg!!.visibility = View.GONE
            cl_bg2!!.visibility = View.GONE
            sek_blure!!.visibility = View.GONE
        }



        iv_flp1!!.setOnClickListener {

            iv_flp1!!.rotationX = iv_flp1!!.rotationX + 180f;
            imageview_id!!.rotationX = imageview_id!!.rotationX + 180f;

        }


        iv_flp2!!.setOnClickListener {
            iv_flp2!!.rotationY = iv_flp2!!.rotationY + 180f;
            imageview_id!!.rotationY = imageview_id!!.rotationY + 180f;


        }


        iv_save!!.setOnClickListener {

            Log.e("TAG", "onCreate: ")

            RLMain!!.setDrawingCacheEnabled(true)
            val bitmap: Bitmap = Bitmap.createBitmap(RLMain!!.getDrawingCache())
            RLMain!!.setDrawingCacheEnabled(false)

            val df: DateFormat = SimpleDateFormat("EEE, d MMM yyyy, HH:mm")
            val date = df.format(Calendar.getInstance().time)
            var uri: Uri = BitmapUtiles.storeImage(
                bitmap,

                this@EditActivity
            );




            val showIntertialads = ShowIntertialads()



            showIntertialads.shaowinr(this@EditActivity, object : ShowIntertialads.CAllBack {
                override fun callbac() {

                    val intent = Intent(this@EditActivity, PreviewActivity::class.java)
                    intent.putExtra("uri",  uri.toString())
                    startActivity(intent)


                }
            })



        }



        sek_rotation!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                imageview_id!!.rotation = p1.toFloat();

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })


        sek_blure!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {


                var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
                var bitmap2: Bitmap? = blur(bmp2, p1.toFloat())
                iv_effect!!.setImageBitmap(bitmap2)


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })



        color_seek_bar!!.setOnColorChangeListener(object : ColorSeekBar.OnColorChangeListener {

            override fun onColorChangeListener(color: Int) {

                Log.e("TAG", "onColorChangeListener: " + color)
                iv_effect!!.setImageBitmap(null)
                iv_effect!!.setBackgroundColor(color)

            }
        })





        val myListData3: ArrayList<Int> = ArrayList()
        myListData3.add(R.drawable.gd_1)
        myListData3.add(R.drawable.gd_4)
        myListData3.add(R.drawable.gd_5)
        myListData3.add(R.drawable.gd_6)
        myListData3.add(R.drawable.gd_7)
        myListData3.add(R.drawable.gd_8)
        myListData3.add(R.drawable.gd_9)
        myListData3.add(R.drawable.gd_10)
        myListData3.add(R.drawable.gd_11)
        myListData3.add(R.drawable.gd_12)
        myListData3.add(R.drawable.gd_13)
        myListData3.add(R.drawable.gd_2)
        myListData3.add(R.drawable.gd_3)
        myListData3.add(R.drawable.gd_0)


        val adapter = MyListAdapter(myListData3, this@EditActivity, object : selectectposion {
            override fun potinodate(postion: Int) {
                iv_effect!!.setImageBitmap(null)
                iv_effect!!.background = resources.getDrawable(postion);

            }

        })
        cl_bg!!.setHasFixedSize(true)
        cl_bg!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cl_bg!!.adapter = adapter






        val myListData43: ArrayList<Int> = ArrayList()
        myListData43.add(R.drawable.bg_s1)
        myListData43.add(R.drawable.bg_s2)
        myListData43.add(R.drawable.bg_s3)
        myListData43.add(R.drawable.bg_s4)
        myListData43.add(R.drawable.bg_s5)
        myListData43.add(R.drawable.bg_s6)
        myListData43.add(R.drawable.bg_s7)
        myListData43.add(R.drawable.bg_s8)



        val adapter2 = MyListAdapter(myListData43, this@EditActivity, object : selectectposion {
            override fun potinodate(postion: Int) {
                iv_effect!!.setImageBitmap(null)
                iv_effect!!.background = resources.getDrawable(postion);

            }

        })
        cl_bg2!!.setHasFixedSize(true)
        cl_bg2!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        cl_bg2!!.adapter = adapter2










        menuLayout!!.setHasFixedSize(true)
        menuLayout!!.setLayoutManager(LinearLayoutManager(this, RecyclerView.HORIZONTAL, false))
        bindDataToAdapter();


    }

    private fun Daabaner() {
        val tinyDB = TinyDB(this@EditActivity)
        val status = tinyDB.getInt("status")
        if (status == 1) {



            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                    .build()
            )


            val adRequest = AdRequest.Builder().build()
            ad_view!!.visibility=View.VISIBLE
            ad_view!!.loadAd(adRequest)


        }
    }

    private fun bindDataToAdapter() {

        val handler = Handler()
        val r = Runnable {


            val t1 = ThumbnailItem()
            val t2 = ThumbnailItem()
            val t3 = ThumbnailItem()
            val t4 = ThumbnailItem()
            val t5 = ThumbnailItem()
            val t6 = ThumbnailItem()
            val t7 = ThumbnailItem()
            val t8 = ThumbnailItem()


            var bmp2 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t1.image = bmp2

            var bmp3 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t2.image = bmp3

            var bmp4 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t3.image = bmp4


            var bmp5 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t4.image = bmp5

            var bmp6 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t5.image = bmp6

            var bmp7 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t6.image = bmp7

            var bmp8 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t7.image = bmp8


            var bmp9 = bitmap!!.copy(bitmap!!.getConfig(), true);
            t8.image = bmp9



            ThumbnailsManager.clearThumbs()
            ThumbnailsManager.addThumb(t1)

            t2.filter = SampleFilters.getStarLitFilter()
            ThumbnailsManager.addThumb(t2)

            t3.filter = SampleFilters.getBlueMessFilter()
            ThumbnailsManager.addThumb(t3)
            t4.filter = SampleFilters.getAweStruckVibeFilter()
            ThumbnailsManager.addThumb(t4)
            t5.filter = SampleFilters.getLimeStutterFilter()
            ThumbnailsManager.addThumb(t5)


            t6.filter = SampleFilters.getNightWhisperFilter()
            ThumbnailsManager.addThumb(t6)

            t7.filter = SampleFilters.getAweStruckVibeFilter()
            ThumbnailsManager.addThumb(t7)


            t8.filter = SampleFilters.getStarLitFilter()
            ThumbnailsManager.addThumb(t8)

            val thumbs: List<ThumbnailItem> = ThumbnailsManager.processThumbs(this@EditActivity)

            val adapter = ThumbnailsAdapter(
                thumbs,
                this@EditActivity as ThumbnailCallback?,
                this@EditActivity
            )
            menuLayout!!.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
        handler.post(r)
    }


    private fun viewTransformation(view: View, event: MotionEvent) {
        when (event.getAction() and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xCoOrdinate = view.x - event.getRawX()
                yCoOrdinate = view.y - event.getRawY()
                start.set(event.getX(), event.getY())
                isOutSide = false
                mode = DRAG
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event).toFloat()
                if (oldDist > 10f) {
                    midPoint(mid, event)
                    mode = ZOOM
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
                d = rotation(event)
            }
            MotionEvent.ACTION_UP -> {
                isZoomAndRotate = false
                if (mode == DRAG) {
                    val x: Float = event.getX()
                    val y: Float = event.getY()
                }
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_MOVE -> if (!isOutSide) {
                if (mode == DRAG) {
                    isZoomAndRotate = false
                    view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate)
                        .setDuration(0).start()
                }
                if (mode == ZOOM && event.getPointerCount() === 2) {
                    val newDist1 = spacing(event)
                    if (newDist1 > 10f) {
                        val scale: Float = (newDist1 / oldDist * view.scaleX).toFloat()
                        view.scaleX = scale
                        view.scaleY = scale
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event)
                        view.rotation = (view.rotation + (newRot - d)) as Float
                    }
                }
            }
        }
    }


    private fun rotation(event: MotionEvent): Float {
        val delta_x: Float = event.getX(0) - event.getX(1)
        val delta_y: Float = event.getY(0) - event.getY(1)
        val radians = Math.atan2(delta_y.toDouble(), delta_x.toDouble())
        return Math.toDegrees(radians).toFloat()
    }

    private fun spacing(event: MotionEvent): Double {
        val x: Float = event.getX(0) - event.getX(1)
        val y: Float = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble())
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x: Float = event.getX(0) + event.getX(1)
        val y: Float = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }

    fun blur(image: Bitmap?, float: Float): Bitmap? {
        if (null == image) return null
        val outputBitmap = Bitmap.createBitmap(image)
        val renderScript: RenderScript = RenderScript.create(this)
        val tmpIn: Allocation = Allocation.createFromBitmap(renderScript, image)
        val tmpOut: Allocation = Allocation.createFromBitmap(renderScript, outputBitmap)

        //Intrinsic Gausian blur filter
        val theIntrinsic: ScriptIntrinsicBlur =
            ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        theIntrinsic.setRadius(float)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }

    override fun onThumbnailClick(filter: Filter?) {
        var bmp9 = bitmap!!.copy(bitmap!!.getConfig(), true);
        imageview_id!!.setImageBitmap(filter!!.processFilter(bmp9));


    }

    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri?): File? {
        val destinationFilename: File =
            File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri!!))
        try {
            context.getContentResolver().openInputStream(uri!!).use { ins ->
                createFileFromStream(
                    ins!!,
                    destinationFilename
                )
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
        return destinationFilename
    }

    fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.e("Save File", it) }
            ex.printStackTrace()
        }
    }

    private fun queryName(context: Context, uri: Uri): String? {
        val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }




}