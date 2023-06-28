package com.whatsycrrop.dpmaker.whatsydirect.fragmnet;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chetsapp.whatsydirect.R;
import com.chetsapp.whatsydirect.activity.ViewImageActivity;
import com.chetsapp.whatsydirect.adsclass.ShowIntertialads;
import com.chetsapp.whatsydirect.fragmnet.TinyDB;

import java.io.File;
import java.util.ArrayList;


public class ImageFragment extends Fragment {
   TinyDB tinyDB;

    RecyclerView listImag;
    ImageAdapter imageAdapter;
    ArrayList<Uri> fileArrayList=new ArrayList<>();

    private TextView tv_syhow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_image, container, false);

        tv_syhow= view.findViewById(R.id.tv_syhow);


        tinyDB= new TinyDB(getActivity());


        listImag=view.findViewById(R.id.listImag);
        listImag.setLayoutManager(new GridLayoutManager(getActivity(),3));


        if(Build.VERSION.SDK_INT<30)
        {


            fileArrayList=getListFiles(new File(
                    Environment.getExternalStorageDirectory().toString()
                            + "/WhatsApp/Media/.Statuses"));

            imageAdapter=new ImageAdapter(fileArrayList);

            if(fileArrayList.size()>0)
            {
                listImag.setAdapter(imageAdapter);
            }
            else
            {
                tv_syhow.setVisibility(View.VISIBLE);
            }




        }

        else
        {

          Uri uri= Uri.parse( tinyDB.getString("fiuri" ));


            if (Build.VERSION.SDK_INT >= 29) {
                DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getActivity(), uri);
                DocumentFile[] documentFiles = fromTreeUri.listFiles();
                Log.e("TAG", "onActivityResult:documentFiles " + documentFiles.length);


                for (int i = 0; i < documentFiles.length; i++) {
                    Log.e("TAG", "onActivityResult:documentFiles " + documentFiles[i].getName());


                    if (documentFiles[i].getUri().getPath().endsWith(".jpg") ||
                            documentFiles[i].getUri().getPath().endsWith(".png")){

                        fileArrayList.add(documentFiles[i].getUri());


                    }
                }
            }


            imageAdapter = new ImageAdapter(fileArrayList);
            if(fileArrayList.size()>0)
            {
                listImag.setAdapter(imageAdapter);
            }
            else
            {
                tv_syhow.setVisibility(View.VISIBLE);
            }








        }




        return view;





    }










    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyView>
    {
        ArrayList<Uri> fileArrayList;


        ImageAdapter(ArrayList<Uri> fileArrayList)
        {
            this.fileArrayList=fileArrayList;
        }

        @NonNull
        @Override
        public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.layout_image,parent,false);
            return new MyView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyView holder, int position) {



            holder.iv_vide.setImageResource(R.drawable.ic_baseline_arrow_circle_down_24);

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

                            startActivity(new Intent(getActivity(), ViewImageActivity.class)
                                    .putExtra("pathImage", fileArrayList.get(position).toString())
                                    .putExtra("selectType", "statusImage"));

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


    private ArrayList<Uri> getListFiles(File parentDir) {
        ArrayList<Uri> inFiles = new ArrayList<>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                Log.e("check", file.getName());
                if (file.getName().endsWith(".jpg")
                        || file.getName().endsWith(".png") ||
                        file.getName().endsWith(".jpeg")
                ) {
                    if (!inFiles.contains(file))
                        inFiles.add(Uri.fromFile(file));
                }
            }
        }
        return inFiles;
    }



}