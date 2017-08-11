package com.example.admin.import2;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 8/12/2017.
 */

public class Persistance extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}