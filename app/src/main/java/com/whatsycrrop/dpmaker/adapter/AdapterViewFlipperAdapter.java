package com.whatsycrrop.dpmaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatsycrrop.dpmaker.R;

public class AdapterViewFlipperAdapter extends BaseAdapter {
   // on below line creating a variable for context, images array, languages array and layout inflater.
   Context context;
   int[] languageImageArray;
   String[] programmingLngArray;
   String[] applinklss;
   LayoutInflater inflater;
   appclick appclick;

   // on below line creating a constructor for it.
   public AdapterViewFlipperAdapter(Context context, int[] images, String[] names,String[]  applinklss, appclick appclick ) {
      this.context = context;
      this.languageImageArray = images;
      this.programmingLngArray = names;
      this.applinklss = applinklss;
      this.inflater = LayoutInflater.from(context);
      this.appclick =appclick;
   }

   @Override
   public int getCount() {
      // on below line returning count for programming languages array.
      return programmingLngArray.length;
   }

   // below method is for getting item
   @Override
   public Object getItem(int position) {
      return null;
   }

   // below method is for getting item id.
   @Override
   public long getItemId(int position) {
      return 0;
   }

   // below method is use to get the view.
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      // on below line inflating our layout file which we have created.
      convertView = inflater.inflate(R.layout.view_flipper_item, null);
      // on below line creating and initializing variables for text view and image view
      TextView programmingLngTV = convertView.findViewById(R.id.idTVProgrammingLanguage);
      ImageView languageIV = convertView.findViewById(R.id.idIVLanguage);
      // on below line setting data to our text view and image view and returning our layout file.
      programmingLngTV.setText(programmingLngArray[position]);
      languageIV.setImageResource(languageImageArray[position]);


      convertView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            appclick.appclic(applinklss[position]);


         }
      });
      return convertView;
   }
}