package com.whatsycrrop.dpmaker.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.whatsycrrop.dpmaker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Varun on 30/06/15.
 *         <p/>
 *         Singleton Class Used to Manage filters and process them all at once
 */
public final class ThumbnailsManager {

    private static List<ThumbnailItem> filterThumbs = new ArrayList<ThumbnailItem>(10);
    private static List<ThumbnailItem> processedThumbs = new ArrayList<ThumbnailItem>(10);

    private ThumbnailsManager() {
    }

    public static void addThumb(ThumbnailItem thumbnailItem) {
        filterThumbs.add(thumbnailItem);
    }

    public static List<ThumbnailItem> processThumbs(Context context) {
        for (ThumbnailItem thumb : filterThumbs) {
            Log.e("TAG", "processThumbs: " +filterThumbs.size());
            float size = context.getResources().getDimension(R.dimen._40sdp);
            Log.e("TAG", "processThumbs1: " +size);
            Log.e("TAG", "processThumbs2: " +thumb.image);
            Log.e("TAG", "processThumbs3: " + thumb.filter);

            thumb.image = Bitmap.createScaledBitmap(thumb.image, (int) size, (int) size, false);
            thumb.image = thumb.filter.processFilter(thumb.image);
            //cropping circle
           // thumb.image = GeneralUtils.generateCircularBitmap(thumb.image);
            processedThumbs.add(thumb);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }
}