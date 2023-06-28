package com.whatsycrrop.dpmaker.whatsydirect.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chetsapp.whatsydirect.R;
import com.chetsapp.whatsydirect.adsclass.ShowIntertialads;
import com.chetsapp.whatsydirect.adsclass.ShowNAtivrbannerAds;
import com.chetsapp.whatsydirect.fragmnet.TinyDB;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SelectActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int RC_CAMERA_PERM = 123;
    private Uri wa_status_uri;
    private TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        tinyDB = new TinyDB(SelectActivity.this);

        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        ShowNAtivrbannerAds showNAtivrbannerAds = new ShowNAtivrbannerAds();
        showNAtivrbannerAds.refreshAd(SelectActivity.this, frameLayout, true);



        onclick();

    }

    private void onclick() {
        findViewById(R.id.strat_Status_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasCameraPermission()) {
                    if (Build.VERSION.SDK_INT >= 30) {


                        if (tinyDB.getString("fiuri").endsWith(".Statuses")) {


                            ShowIntertialads showIntertialads = new ShowIntertialads();
                            showIntertialads.shaowinr(SelectActivity.this, new ShowIntertialads.CAllBack() {
                                @Override
                                public void callbac() {
                                    startActivity(new Intent(SelectActivity.this, StatusActivity.class));

                                }
                            });


                        } else {
                            checkWhazAppPermission();
                        }


                    } else {


                        ShowIntertialads showIntertialads = new ShowIntertialads();
                        showIntertialads.shaowinr(SelectActivity.this, new ShowIntertialads.CAllBack() {
                            @Override
                            public void callbac() {
                                startActivity(new Intent(SelectActivity.this, StatusActivity.class));
                            }
                        });


                    }

                } else {

                    EasyPermissions.requestPermissions(
                            SelectActivity.this,
                            getString(R.string.rationale_camera),
                            RC_CAMERA_PERM,
                            LOCATION_AND_CONTACTS);
                }
            }
        });

        findViewById(R.id.strat_MyStatus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasCameraPermission()) {

                    ShowIntertialads showIntertialads = new ShowIntertialads();
                    showIntertialads.shaowinr(SelectActivity.this, new ShowIntertialads.CAllBack() {
                        @Override
                        public void callbac() {
                            startActivity(new Intent(SelectActivity.this, SaveActivity.class));
                        }
                    });


                } else {

                    EasyPermissions.requestPermissions(
                            SelectActivity.this,
                            getString(R.string.rationale_camera),
                            RC_CAMERA_PERM,
                            LOCATION_AND_CONTACTS);
                }

            }
        });
    }


    private void checkWhazAppPermission() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        wa_status_uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses");

        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, wa_status_uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, 10001);
    }


    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size());


        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }

        if (resultCode == RESULT_OK) {
            if (data != null) {

                Uri uri = data.getData();


                if (uri.getPath().endsWith(".Statuses")) {
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    }

                    tinyDB.putString("fiuri", uri.toString());


                    startActivity(new Intent(SelectActivity.this, StatusActivity.class));


                } else {
                    Toast.makeText(SelectActivity.this, "Please Use Status folder only!", Toast.LENGTH_SHORT).show();
                }


            } else {

                Toast.makeText(SelectActivity.this, "showWrongPathDialog", Toast.LENGTH_SHORT).show();
            }

        } else {


        }


    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}