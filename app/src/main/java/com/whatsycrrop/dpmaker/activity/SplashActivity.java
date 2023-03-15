package com.whatsycrrop.dpmaker.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsycrrop.dpmaker.R;
import com.whatsycrrop.dpmaker.adsclass.AdmobgoogleAdsall;
import com.whatsycrrop.dpmaker.databas.DataStatuses;
import com.whatsycrrop.dpmaker.databas.WhtasyCrop;
import com.whatsycrrop.dpmaker.utiles.TinyDB;

public class SplashActivity extends AppCompatActivity {

    public static Integer countvar0  = 0;
    private TinyDB tinyDB;
    LottieAnimationView image_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image_view= findViewById(R.id.image_view);


        if (checkConnection(SplashActivity.this)) {

            image_view.setVisibility(View.VISIBLE);
            image_view.playAnimation();
            initDAta();



        } else {



            Dialog dialog = new Dialog(SplashActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.retry_dialog);

            ConstraintLayout tv_trysgai= dialog.findViewById(R.id.tv_trysgai);
            tv_trysgai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkConnection(SplashActivity.this)) {

                        dialog.dismiss();
                        image_view.setVisibility(View.VISIBLE);
                        image_view.playAnimation();
                        initDAta();

                    }
                    else

                    {

                    }

                }
            });

            dialog.show();

        }


    }


    private void initDAta() {
        tinyDB=new TinyDB(SplashActivity.this);


        tinyDB.putInt("back", 0);

        if (tinyDB.getInt("status") == 0) {

            tinyDB.putInt("status", 2);
            tinyDB.putInt("count", 3);

            tinyDB.putInt("natstatus", 2);
            tinyDB.putInt("allshow", 2);


        }
        DataStatuses dataStatuses = new DataStatuses();

//
//        WhtasyCrop chatDirect=  new WhtasyCrop(1,4,1);
//         dataStatuses.adddata(chatDirect);



        DatabaseReference rootRef = dataStatuses.getdata();
        rootRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if (dataSnapshot.getChildren().toString().equals("0")) {
                    Log.e("TAG", "onDataChange22: " + dataSnapshot.getChildren());

                    tinyDB.putInt("status",1);
                    tinyDB.putInt("count",4);
                    tinyDB.putInt("natstatus",2);
                    tinyDB.putInt("allshow", 1);


                } else {

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                        Log.e(
                                "TAG",
                                "onDataChange: " + Integer.valueOf(childSnapshot.child("status").getValue().toString())
                        );
                        tinyDB.putInt(
                                "status",
                                Integer.valueOf(childSnapshot.child("status").getValue().toString())
                        );
                        tinyDB.putInt(
                                "count",
                                Integer.valueOf(childSnapshot.child("count").getValue().toString())
                        );
                        tinyDB.putInt(
                                "natstatus",
                                Integer.valueOf(childSnapshot.child("nat").getValue().toString())
                        );


                        tinyDB.putInt("allshow", 1);
                    }
                }



            }

        });
        if (tinyDB.getInt("status") == 1) {

            AdmobgoogleAdsall.getsinterface().loadAd(SplashActivity.this, 1);
        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                if(tinyDB.getInt("allshow")!=1  &&  tinyDB.getInt("status")!=1 )
                {

                    Log.e("TAG", "parsssss: " );

                    tinyDB.putInt("status",1);
                    tinyDB.putInt("count",3);
                    tinyDB.putInt("natstatus",1);
                    tinyDB.putInt("allshow", 1);

                    if (tinyDB.getInt("status") == 1) {

                        AdmobgoogleAdsall.getsinterface().loadAd(SplashActivity.this, 1);
                    }

                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                    finish();



                }
                else

                {
                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                    finish();

                }





            }
        },6000);




    }


    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {


            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                return true;
            }
        }
        return false;
    }
    @Override
    public void onBackPressed() {

    }


}