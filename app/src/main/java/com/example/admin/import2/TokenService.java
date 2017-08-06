package com.example.admin.import2;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Admin on 8/5/2017.
 */

public class TokenService extends FirebaseInstanceIdService {
    private static final String TAG = "FCMService";

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        MainActivity.tokenID = fcmToken;

        Log.d(TAG,fcmToken);
        //Save or send FCM registration token
        sendTokenToDatabase(fcmToken);
    }
    public void sendTokenToDatabase(String token){

      //  DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
      //  mData.child("users").child(MainActivity.userID).child("TokenID").setValue(token);
    }
}
