package com.example.admin.import2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NewReminderActivity extends AppCompatActivity {

    Intent i = this.getIntent();



        //String receiverName = extras.getString("ReceiverName");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        String receiverUID = getIntent().getExtras().getString("ReceiverUID");
       Toast.makeText(getApplicationContext(), receiverUID, Toast.LENGTH_SHORT).show();
    }
}

