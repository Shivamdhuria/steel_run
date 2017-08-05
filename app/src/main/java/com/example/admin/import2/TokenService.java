package com.example.admin.import2;

import android.util.Log;

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

        Log.d(TAG,"FCMtoken"+fcmToken);
        //Save or send FCM registration token
    }
}
