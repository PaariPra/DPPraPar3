package com.whatsycrrop.dpmaker.utiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BitmapUtiles {

    private static final String CHILD_DIR = "images";
    private static final String TEMP_FILE_NAME = "img";
    private static final String FILE_EXTENSION = ".png";

    private static final int COMPRESS_QUALITY = 100;


    public static Bitmap getBitmap( final Drawable dwee) {
        Drawable drawable = dwee;
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public static  Uri saveToCacheAndGetUri(Bitmap bitmap, @Nullable String name,Context context) {
        File file = saveImgToCache(bitmap, name, context);
        return getImageUri(file, name,context);
    }

    public  static  File saveImgToCache(Bitmap bitmap, @Nullable String name, Context context) {
        File cachePath = null;
        String fileName = TEMP_FILE_NAME;
        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        }
        try {
            cachePath = new File(context.getCacheDir(), CHILD_DIR);
            cachePath.mkdirs();

            FileOutputStream stream = new FileOutputStream(cachePath + "/" + fileName + ".png");
            bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY, stream);
            stream.close();
        } catch (IOException e) {

        }
        return cachePath;
    }
  public  static  File saveImgToCache2(Bitmap bitmap, @Nullable String name, Context context) {
        File cachePath = null;
        String fileName = TEMP_FILE_NAME;

        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        }

        try {
          File  cachePath2 = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS);
            if(!cachePath2.exists())
            {
                Log.e("TAG", "saveImgToCache2:__ ");
                cachePath2.mkdir();

            }

            cachePath=new File(cachePath2.getAbsolutePath(),"WhtssiCropy");
            if(!cachePath.exists())
            {
                cachePath.mkdir();


            }

            FileOutputStream stream = new FileOutputStream(cachePath + "/" + fileName + ".png");
            bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY, stream);
            stream.close();


        } catch (IOException e) {

        }
        return cachePath;
    }


    public static  Uri saveToCacheAndGetUri2(Bitmap bitmap, @Nullable String name,Context context) {
        File file = saveImgToCache2(bitmap, name, context);
        return getImageUri2(file, name,context);
    }


    private static  Uri  getImageUri(File fileDir, @Nullable String name, Context context) {

        String fileName = ""+"phortedot";
        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        }
        File newFile = new File(fileDir, fileName + ".png");



        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);


    }

    private static  Uri  getImageUri2(File fileDir, @Nullable String name, Context context) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        String fileName = ""+ date;
        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        }
        File newFile = new File(fileDir, fileName + ".png");
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);


    }


    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }

    public static  Uri   storeImage(Bitmap image,Context context) {
        File pictureFile = getOutputMediaFile(context);

        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", pictureFile);




    }


    private static File getOutputMediaFile(Context context){

//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
//                + "/Download/WhtssiCropy");
//
//        if (! mediaStorageDir.exists()){
//            if (! mediaStorageDir.mkdirs()){
//                return null;
//            }
//        }

        File  cachePath2 = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS);
        if(!cachePath2.exists())
        {
            Log.e("TAG", "saveImgToCache2:__ ");
            cachePath2.mkdir();

        }

       File cachePath=new File(cachePath2.getAbsolutePath(),"WhtssiCropy");
        if(!cachePath.exists())
        {
            cachePath.mkdir();


        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(cachePath.getPath() + File.separator + mImageName);
        return mediaFile;
    }


}
