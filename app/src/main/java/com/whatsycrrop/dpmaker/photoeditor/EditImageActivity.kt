package com.whatsycrrop.dpmaker.photoeditor

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull

import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener


import ja.burhanrashid52.photoeditor.PhotoEditorView
import androidx.recyclerview.widget.RecyclerView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import ja.burhanrashid52.photoeditor.TextStyleBuilder
import ja.burhanrashid52.photoeditor.ViewType
import androidx.core.content.FileProvider
import androidx.core.content.ContextCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager

import ja.burhanrashid52.photoeditor.SaveSettings
import ja.burhanrashid52.photoeditor.PhotoEditor.OnSaveListener
import ja.burhanrashid52.photoeditor.shape.ShapeType
import ja.burhanrashid52.photoeditor.PhotoFilter

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder
import java.io.File
import java.io.IOException
import java.lang.Exception
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.utiles.TinyDB
import ja.burhanrashid52.photoeditor.PhotoEditor
import java.util.*


class EditImageActivity : BaseActivity(), OnPhotoEditorListener, View.OnClickListener,
    PropertiesBSFragment.Properties, ShapeBSFragment.Properties, EmojiBSFragment.EmojiListener {

    var mPhotoEditor: PhotoEditor? = null
    private var mPhotoEditorView: PhotoEditorView? = null
    private var mPropertiesBSFragment: PropertiesBSFragment? = null
    private var mShapeBSFragment: ShapeBSFragment? = null
    private var mShapeBuilder: ShapeBuilder? = null
    private var mEmojiBSFragment: EmojiBSFragment? = null
    private var mWonderFont: Typeface? = null
    private var mRootView: ConstraintLayout? = null
    private val mConstraintSet = ConstraintSet()
    private var mIsFilterVisible = false

    private var SHAPE: LinearLayout? = null
    private var TEXT: LinearLayout? = null
    private var ERASER: LinearLayout? = null
    private var EMOJI: LinearLayout? = null

    private var ic_back2: ImageView? = null
    var ad_view: AdView?=null;
    @VisibleForTesting
    var mSaveImageUri: Uri? = null
    private var mSaveFileHelper: FileSaveHelper? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_image)

        initViews()
        Daabaner();
        handleIntentImage(mPhotoEditorView?.source)
        mWonderFont = Typeface.createFromAsset(assets, "beyond_wonderland.ttf")
        mPropertiesBSFragment = PropertiesBSFragment()
        mEmojiBSFragment = EmojiBSFragment()
        mShapeBSFragment = ShapeBSFragment()
        mEmojiBSFragment?.setEmojiListener(this)
        mPropertiesBSFragment?.setPropertiesChangeListener(this)
        mShapeBSFragment?.setPropertiesChangeListener(this)

        val pinchTextScalable = intent.getBooleanExtra(PINCH_TEXT_SCALABLE_INTENT_KEY, true)


        mPhotoEditor = mPhotoEditorView?.run {
            PhotoEditor.Builder(this@EditImageActivity, this)
                .setPinchTextScalable(pinchTextScalable)
                .build()
        }
        mPhotoEditor?.setOnPhotoEditorListener(this)


        var uri: Uri = Uri.parse(intent.extras!!.getString("uri"))

        mPhotoEditorView?.source?.setImageURI(uri)








        mSaveFileHelper = FileSaveHelper(this)



        SHAPE!!.setOnClickListener {

            mPhotoEditor?.setBrushDrawingMode(true)
            mShapeBuilder = ShapeBuilder()
            mPhotoEditor?.setShape(mShapeBuilder)
            showBottomSheetDialogFragment(mShapeBSFragment)
        }

        EMOJI!!.setOnClickListener {

            showBottomSheetDialogFragment(mEmojiBSFragment)
        }

        TEXT!!.setOnClickListener {
            val textEditorDialogFragment = TextEditorDialogFragment.show(this)
            textEditorDialogFragment.setOnTextEditorListener(object :
                TextEditorDialogFragment.TextEditorListener {
                override fun onDone(inputText: String?, colorCode: Int) {
                    val styleBuilder = TextStyleBuilder()
                    styleBuilder.withTextColor(colorCode)
                    mPhotoEditor?.addText(inputText, styleBuilder)

                }
            })
        }
        ERASER!!.setOnClickListener {

            mPhotoEditor?.brushEraser()

        }


    }

    private fun handleIntentImage(source: ImageView?) {
        if (intent == null) {
            return;
        }

        when (intent.action) {
            Intent.ACTION_EDIT, ACTION_NEXTGEN_EDIT -> {
                try {
                    val uri = intent.data
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver, uri
                    )
                    source?.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            else -> {
                val intentType = intent.type
                if (intentType != null && intentType.startsWith("image/")) {
                    val imageUri = intent.data
                    if (imageUri != null) {
                        source?.setImageURI(imageUri)
                    }
                }
            }
        }
    }

    private fun initViews() {
        ad_view=findViewById(R.id.ad_view);
        mPhotoEditorView = findViewById(R.id.photoEditorView)
        mRootView = findViewById(R.id.rootView)

        val imgUndo: ImageView = findViewById(R.id.imgUndo)
        imgUndo.setOnClickListener(this)
        val imgRedo: ImageView = findViewById(R.id.imgRedo)
        imgRedo.setOnClickListener(this)
        val imgSave: ImageView = findViewById(R.id.imgSave)
        imgSave.setOnClickListener(this)
        SHAPE = findViewById(R.id.SHAPE);
        TEXT = findViewById(R.id.TEXT);
        ERASER = findViewById(R.id.ERASER);
        EMOJI = findViewById(R.id.EMOJI);
        ic_back2 = findViewById(R.id.ic_back2);
        ic_back2!!.setOnClickListener { finish() }

    }

    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
        val textEditorDialogFragment =
            TextEditorDialogFragment.show(this, text.toString(), colorCode)
        textEditorDialogFragment.setOnTextEditorListener(object :
            TextEditorDialogFragment.TextEditorListener {
            override fun onDone(inputText: String?, colorCode: Int) {
                val styleBuilder = TextStyleBuilder()
                styleBuilder.withTextColor(colorCode)
                if (rootView != null) {
                    mPhotoEditor?.editText(rootView, inputText, styleBuilder)
                }

            }
        })
    }

    override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
        Log.d(
            TAG,
            "onAddViewListener() called with: viewType = [$viewType], numberOfAddedViews = [$numberOfAddedViews]"
        )
    }

    override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
        Log.d(
            TAG,
            "onRemoveViewListener() called with: viewType = [$viewType], numberOfAddedViews = [$numberOfAddedViews]"
        )
    }

    override fun onStartViewChangeListener(viewType: ViewType?) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [$viewType]")
    }

    override fun onStopViewChangeListener(viewType: ViewType?) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [$viewType]")
    }

    override fun onTouchSourceImage(event: MotionEvent?) {
        Log.d(TAG, "onTouchView() called with: event = [$event]")
    }

    @SuppressLint("NonConstantResourceId", "MissingPermission")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.imgUndo -> mPhotoEditor?.undo()
            R.id.imgRedo -> mPhotoEditor?.redo()
            R.id.imgSave -> saveImage()


        }
    }


    private fun buildFileProviderUri(uri: Uri): Uri {
        if (FileSaveHelper.isSdkHigherThan28()) {
            return uri
        }
        val path: String = uri.path ?: throw IllegalArgumentException("URI Path Expected")

        return FileProvider.getUriForFile(
            this,
            FILE_PROVIDER_AUTHORITY,
            File(path)
        )
    }

    private fun Daabaner() {
        val tinyDB = TinyDB(this@EditImageActivity)
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

    @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
    private fun saveImage() {
        val fileName = "Test" + ".png"
        val hasStoragePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        if (hasStoragePermission || FileSaveHelper.isSdkHigherThan28()) {
            showLoading("Saving...")

            var cachePath: File? = null
            val cachePath2 = File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/" + Environment.DIRECTORY_DOWNLOADS
            )
            if (!cachePath2.exists()) {

                cachePath2.mkdir()
            }


            cachePath = File(cachePath2.absolutePath, "WhtssiCropy")
            if (!cachePath.exists()) {
                cachePath.mkdir()
            }





            mPhotoEditor!!.saveAsFile("$cachePath/$fileName", object : OnSaveListener {
                override fun onSuccess(@NonNull imagePath: String) {


                    mSaveFileHelper?.notifyThatFileIsNowPubliclyAvailable(
                        contentResolver
                    )
                    hideLoading()
                    var uri: Uri = Uri.fromFile(File(imagePath))

                    mSaveImageUri = uri
                    mPhotoEditorView?.source?.setImageURI(mSaveImageUri)


                    var intent: Intent;
                    intent = Intent()
                    intent.putExtra("uri", uri.toString())
                    intent.putExtra("path", imagePath)
                    setResult(RESULT_OK, intent)
                    finish()
//
                }

                override fun onFailure(@NonNull exception: Exception) {
                    Log.e("PhotoEditor", "Failed to save Image")
                }
            })


//            mSaveFileHelper?.createFile(fileName, object : FileSaveHelper.OnFileCreateResult {
//
//                @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
//                override fun onFileCreateResult(
//                    created: Boolean,
//                    filePath: String?,
//                    error: String?,
//                    uri: Uri?
//                ) {
//                    if (created && filePath != null) {
//                        val saveSettings = SaveSettings.Builder()
//                            .setClearViewsEnabled(true)
//                            .setTransparencyEnabled(true)
//                            .build()
//
//                        mPhotoEditor?.saveAsFile(
//                            filePath,
//                            saveSettings,
//                            object : OnSaveListener {
//                                override fun onSuccess(imagePath: String) {
//
//
//                                    mSaveFileHelper?.notifyThatFileIsNowPubliclyAvailable(
//                                        contentResolver
//                                    )
//                                    hideLoading()
//                                    mSaveImageUri = uri
//                                    mPhotoEditorView?.source?.setImageURI(mSaveImageUri)
//
//
//                                    var intent: Intent;
//                                    intent = Intent()
//                                    intent.putExtra("uri", uri.toString())
//                                    intent.putExtra("path", filePath)
//                                    setResult(RESULT_OK, intent)
//                                    finish()
//
//
//
//                                }
//
//                                override fun onFailure(exception: Exception) {
//                                    hideLoading()
//                                    showSnackbar("Failed to save Image")
//                                }
//                            })
//                    } else {
//                        hideLoading()
//                        error?.let { showSnackbar(error) }
//                    }
//                }
//            })


        } else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    // TODO(lucianocheng): Replace onActivityResult with Result API from Google
    //                     See https://developer.android.com/training/basics/intents/result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST -> {
                    mPhotoEditor?.clearAllViews()
                    val photo = data?.extras?.get("data") as Bitmap?
                    mPhotoEditorView?.source?.setImageBitmap(photo)
                }
                PICK_REQUEST -> try {
                    mPhotoEditor?.clearAllViews()
                    val uri = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver, uri
                    )
                    mPhotoEditorView?.source?.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onColorChanged(colorCode: Int) {
        mPhotoEditor?.setShape(mShapeBuilder?.withShapeColor(colorCode))

    }

    override fun onOpacityChanged(opacity: Int) {
        mPhotoEditor?.setShape(mShapeBuilder?.withShapeOpacity(opacity))

    }

    override fun onShapeSizeChanged(shapeSize: Int) {
        mPhotoEditor?.setShape(mShapeBuilder?.withShapeSize(shapeSize.toFloat()))

    }

    override fun onShapePicked(shapeType: ShapeType?) {
        mPhotoEditor?.setShape(mShapeBuilder?.withShapeType(shapeType))
    }

    override fun onEmojiClick(emojiUnicode: String?) {
        mPhotoEditor?.addEmoji(emojiUnicode)

    }


    @SuppressLint("MissingPermission")
    override fun isPermissionGranted(isGranted: Boolean, permission: String?) {
        if (isGranted) {
            saveImage()
        }
    }

    @SuppressLint("MissingPermission")
    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.msg_save_image))
        builder.setPositiveButton("Save") { _: DialogInterface?, _: Int -> saveImage() }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.setNeutralButton("Discard") { _: DialogInterface?, _: Int -> finish() }
        builder.create().show()
    }


//    override fun onToolSelected(toolType: ToolType?) {
//        when (toolType) {
//            ToolType.SHAPE -> {
//                mPhotoEditor?.setBrushDrawingMode(true)
//                mShapeBuilder = ShapeBuilder()
//                mPhotoEditor?.setShape(mShapeBuilder)
//                mTxtCurrentTool?.setText(R.string.label_shape)
//                showBottomSheetDialogFragment(mShapeBSFragment)
//            }
//            ToolType.TEXT -> {
//                val textEditorDialogFragment = TextEditorDialogFragment.show(this)
//                textEditorDialogFragment.setOnTextEditorListener(object : TextEditorDialogFragment.TextEditorListener {
//                    override fun onDone(inputText: String?, colorCode: Int) {
//                        val styleBuilder = TextStyleBuilder()
//                        styleBuilder.withTextColor(colorCode)
//                        mPhotoEditor?.addText(inputText, styleBuilder)
//                        mTxtCurrentTool?.setText(R.string.label_text)
//                    }
//                })
//            }
//            ToolType.ERASER -> {
//                mPhotoEditor?.brushEraser()
//                mTxtCurrentTool?.setText(R.string.label_eraser_mode)
//            }
//            ToolType.FILTER -> {
//                mTxtCurrentTool?.setText(R.string.label_filter)
//                showFilter(true)
//            }
//            ToolType.EMOJI -> showBottomSheetDialogFragment(mEmojiBSFragment)
//            ToolType.STICKER -> showBottomSheetDialogFragment(mStickerBSFragment)
//        }
//    }

    private fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment?) {
        if (fragment == null || fragment.isAdded) {
            return
        }
        fragment.show(supportFragmentManager, fragment.tag)
    }

    private fun showFilter(isVisible: Boolean) {
        mIsFilterVisible = isVisible
        mConstraintSet.clone(mRootView)

    }

    override fun onBackPressed() {
        val isCacheEmpty =
            mPhotoEditor?.isCacheEmpty ?: throw IllegalArgumentException("isCacheEmpty Expected")

        if (mIsFilterVisible) {
            showFilter(false)

        } else if (!isCacheEmpty) {
            showSaveDialog()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private val TAG = EditImageActivity::class.java.simpleName
        const val FILE_PROVIDER_AUTHORITY = "com.burhanrashid52.photoediting.fileprovider"
        private const val CAMERA_REQUEST = 52
        private const val PICK_REQUEST = 53
        const val ACTION_NEXTGEN_EDIT = "action_nextgen_edit"
        const val PINCH_TEXT_SCALABLE_INTENT_KEY = "PINCH_TEXT_SCALABLE"
    }
}