package com.whatsycrrop.dpmaker.whatsydirect.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chetsapp.whatsydirect.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ViewImageActivity extends AppCompatActivity {

  private String pathImage;
  private File file;
  private LinearLayout downloadImage, shareImage, deleteImage;
  private Uri frifile;
  private Dialog dialog;
  private ConstraintLayout cl_1, cl_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        initData();
    }

    private void initData() {
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/WhatsStatus");


        if (!file.exists()) {
            file.mkdirs();
        }


        downloadImage = findViewById(R.id.downloadImage);
        shareImage = findViewById(R.id.shareImage);
        deleteImage = findViewById(R.id.deleteImage);

        if (getIntent().getStringExtra("selectType").equals("statusImage")) {
            downloadImage.setVisibility(View.VISIBLE);
            shareImage.setVisibility(View.GONE);
            deleteImage.setVisibility(View.GONE);
        } else {
            downloadImage.setVisibility(View.GONE);
            shareImage.setVisibility(View.VISIBLE);
            deleteImage.setVisibility(View.VISIBLE);


        }

        pathImage = getIntent().getStringExtra("pathImage");


        Glide.with(ViewImageActivity.this).load(pathImage).into((ImageView) findViewById(R.id.image));

        findViewById(R.id.downloadImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    new Data().execute();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: 90-----" + e.getMessage());
                }
            }
        });

        if (Build.VERSION.SDK_INT < 30   ) {

        } else {


            deleteImage.setVisibility(View.GONE);




        }



        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File fdelete = new File(pathImage);
                if (fdelete.exists()) {
                    fdelete.delete();
                    Toast.makeText(ViewImageActivity.this, "Deleted...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                Uri mUri = FileProvider.getUriForFile(ViewImageActivity.this,
                        getPackageName() + ".fileprovider", new File(pathImage));
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, mUri);
                startActivity(Intent.createChooser(shareIntent, "Share image"));
            }
        });

    }


    public class Data extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    dialog = new Dialog(ViewImageActivity.this);

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.select_dialog);

                    cl_1 = dialog.findViewById(R.id.cl_1);
                    cl_2 = dialog.findViewById(R.id.cl_2);

                    cl_1.setVisibility(View.VISIBLE);
                    cl_2.setVisibility(View.GONE);


                    dialog.show();
                }
            });


        }

        @Override
        protected String doInBackground(String... strings) {
            copyFile(pathImage, file.getAbsolutePath());
            return "";


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    cl_1.setVisibility(View.GONE);
                    cl_2.setVisibility(View.VISIBLE);
                    TextView tv_path = dialog.findViewById(R.id.tv_path);

                    tv_path.setText("" + new File(frifile.toString()).getAbsolutePath());
                    TextView tv_ok = dialog.findViewById(R.id.tv_ok);

                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }
            });


        }
    }

    public boolean copyFile(String sourceFile, String destFile) {


        String str2;

        if (Build.VERSION.SDK_INT < 30) {

            str2 = "/" + System.currentTimeMillis() + ".jpg";

        } else {
            str2 = "/" + sourceFile.split("%")[sourceFile.split("%").length - 1];

        }


        frifile = Uri.fromFile(new File(destFile + str2));


        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(sourceFile));

            OutputStream outputStream = getContentResolver().openOutputStream(frifile, "w");


            byte[] bytes = new byte[1024];

            while (true) {

                int read = inputStream.read(bytes);
                if (read > 0) {
                    outputStream.write(bytes, 0, read);

                } else {

                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();


                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(frifile);
                    sendBroadcast(intent);

                    return true;

                }


            }
        } catch (FileNotFoundException e) {
            Log.e("TAG", "copyFile: FileNotFoundException" + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.e("TAG", "copyFile: FileNotFoundException" + e.getMessage());
            e.printStackTrace();
            return false;
        }


    }


    @Override
    public void onBackPressed() {
        finish();
    }
}