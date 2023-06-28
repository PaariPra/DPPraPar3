package com.whatsycrrop.dpmaker.whatsydirect.fragmnet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetsapp.whatsydirect.R;
import com.chetsapp.whatsydirect.activity.ViewVideoActivity;
import com.chetsapp.whatsydirect.adsclass.ShowIntertialads;

import java.io.File;
import java.util.ArrayList;

public class VideoSaveFragment extends Fragment {

    RecyclerView listImag;
    ImageAdapter imageAdapter;
    ArrayList<File> fileArrayList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);


        listImag=view.findViewById(R.id.listImag);
        listImag.setLayoutManager(new GridLayoutManager(getActivity(),3));

        fileArrayList=getListFiles(new File(
                Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+
                        Environment.DIRECTORY_DOWNLOADS+"/WhatsStatus"));


        imageAdapter=new ImageAdapter();
        listImag.setAdapter(imageAdapter);

        return view;
    }


    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyView>
    {

        @NonNull
        @Override
        public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.layout_image,parent,false);
            return new MyView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyView holder, int position) {
            holder.iv_vide.setVisibility(View.VISIBLE);

            Glide.with(getActivity())
                    .load(fileArrayList.get(position))
                    .into(holder.imageThum);

            holder.imageThum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    ShowIntertialads showIntertialads = new ShowIntertialads();
                    showIntertialads.shaowinr(getActivity(), new ShowIntertialads.CAllBack() {
                        @Override
                        public void callbac() {



                            startActivity(new Intent(getActivity(), ViewVideoActivity.class)
                                    .putExtra("pathImage",fileArrayList.get(position).toString())
                                    .putExtra("selectType","saveVideo"));

                        }
                    });



                }
            });


        }


        @Override
        public int getItemCount() {
            return fileArrayList.size();
        }

        public class MyView extends RecyclerView.ViewHolder {

            ImageView imageThum,iv_vide;

            public MyView(@NonNull View itemView) {
                super(itemView);

                imageThum=itemView.findViewById(R.id.imageThum);
                iv_vide=itemView.findViewById(R.id.iv_vide);

            }
        }
    }


    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                Log.e("check", file.getName());
                if (file.getName().endsWith(".mp4")) {
                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }
        return inFiles;
    }



}