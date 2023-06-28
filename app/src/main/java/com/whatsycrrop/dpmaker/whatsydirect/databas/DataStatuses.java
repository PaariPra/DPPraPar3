package com.whatsycrrop.dpmaker.whatsydirect.databas;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class DataStatuses {
    private DatabaseReference databaseReference;

    public DataStatuses() {


        databaseReference = FirebaseDatabase.getInstance().getReference(ChatDirect.class.getSimpleName());

    }
    public  DatabaseReference getdata()
    {
        return databaseReference.child("ChatDirect");
    }



    public Task<Void> adddata(ChatDirect status) {


        return  databaseReference.child("ChatDirect").push().setValue(status);

    }


    public Task<Void> update(String key, HashMap<String, Object> hashmap) {

        return databaseReference.child("ChatDirect").child(key).updateChildren(hashmap);


    }


}
