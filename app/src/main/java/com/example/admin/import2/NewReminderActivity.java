package com.example.admin.import2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NewReminderActivity extends AppCompatActivity {


   // String receiverUID = getIntent().getStringExtra("ReceiverUID");
   // String receiverName = getIntent().getStringExtra("ReceiverName");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
      //  Toast.makeText(getApplicationContext(), receiverName, Toast.LENGTH_SHORT).show();
    }
}
