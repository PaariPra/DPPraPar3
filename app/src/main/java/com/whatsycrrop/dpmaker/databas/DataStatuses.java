package com.whatsycrrop.dpmaker.databas;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class DataStatuses {
    private DatabaseReference databaseReference;

    public DataStatuses() {


        databaseReference = FirebaseDatabase.getInstance().getReference(WhtasyCrop.class.getSimpleName());

    }
    public  DatabaseReference getdata()
    {
        return databaseReference.child("WhtasyCrop");
    }



    public Task<Void> adddata(WhtasyCrop status) {


        return  databaseReference.child("WhtasyCrop").push().setValue(status);

    }


    public Task<Void> update(String key, HashMap<String, Object> hashmap) {

        return databaseReference.child("WhtasyCrop").child(key).updateChildren(hashmap);


    }


}
