package com.whatsycrrop.dpmaker.whatsydirect.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.chetsapp.whatsydirect.R;
import com.chetsapp.whatsydirect.adsclass.AdmobgoogleAdsall;
import com.chetsapp.whatsydirect.databas.ChatDirect;
import com.chetsapp.whatsydirect.databas.DataStatuses;
import com.chetsapp.whatsydirect.fragmnet.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    public static Integer countvar0  = 0;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tinyDB=new TinyDB(SplashActivity.this);


        if (tinyDB.getInt("status") == 0) {

            tinyDB.putInt("status", 2);
            tinyDB.putInt("count", 3);




        }

        DataStatuses dataStatuses = new DataStatuses();





//        ChatDirect chatDirect= new ChatDirect(1,4);
//         dataStatuses.adddata(chatDirect);






        DatabaseReference rootRef = dataStatuses.getdata();
        rootRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {


                    Log.e("TAG", "onDataChange: "+ Integer.valueOf(childSnapshot.child("status").getValue().toString()) );

                    tinyDB.putInt("status", Integer.valueOf(childSnapshot.child("status").getValue().toString()));
                    tinyDB.putInt("count", Integer.valueOf(childSnapshot.child("count").getValue().toString()));



                }



            }

        });











        if (tinyDB.getInt("status") == 1) {

            AdmobgoogleAdsall.getsinterface().loadAd(SplashActivity.this, 1);
        }









        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SelectActivity.class));
                finish();
            }
        },6000);


    }

    @Override
    public void onBackPressed() {

    }
}