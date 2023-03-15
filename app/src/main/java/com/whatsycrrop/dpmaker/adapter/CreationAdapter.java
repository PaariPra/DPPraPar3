package com.whatsycrrop.dpmaker.adapter;
  

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsycrrop.dpmaker.R;
import com.whatsycrrop.dpmaker.adsclass.ShowIntertialads;
import com.whatsycrrop.dpmaker.interfaceces.selectectposion;
import com.whatsycrrop.dpmaker.activity.PreviewActivity;

import java.io.File;
import java.util.ArrayList;


public class CreationAdapter extends RecyclerView.Adapter<CreationAdapter.ViewHolder>{
    private ArrayList<File> listdata;
    private Activity activity;
   private selectectposion selectectposion;

   // RecyclerView recyclerView;  
    public CreationAdapter(ArrayList<File> listdata, Activity activity, selectectposion selectectposion) {
        this.listdata = listdata;
        this.activity = activity;
        this.selectectposion = selectectposion;
    }
    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.image_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);  
        return viewHolder;  
    }  
  
    @Override  
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final File myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        holder.imageView.setImageURI(Uri.parse(listdata.get(position).getAbsolutePath()));



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View view) {


                ShowIntertialads showIntertialads = new ShowIntertialads();
                showIntertialads.shaowinr(activity, new ShowIntertialads.CAllBack() {
                    @Override
                    public void callbac() {


                        activity.startActivity(new Intent(activity, PreviewActivity.class)
                                .putExtra("uri",  FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", listdata.get(position)).toString())

                                .putExtra("path", listdata.get(position).getAbsolutePath())
                        );


                    }
                });






            }  
        });

        holder.iv_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File extrafile = new File(listdata.get(position).getAbsolutePath());
                extrafile.delete();
                listdata.remove(position);
                notifyDataSetChanged();



            }
        });






    }  
  
  
    @Override  
    public int getItemCount() {  
        return listdata.size();
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder {  
        public ImageView imageView;  
        public ImageView iv_delet;
        public TextView textView;
        public ConstraintLayout relativeLayout;
        public ViewHolder(View itemView) {  
            super(itemView);  
            this.imageView =  itemView.findViewById(R.id.iv_show);
            this.iv_delet = itemView.findViewById(R.id.iv_delet);
            this.textView = itemView.findViewById(R.id.textView2);
            relativeLayout = itemView.findViewById(R.id.iv_shaoee);
        }  
    }




}  