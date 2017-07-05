package com.example.admin.import2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreviewActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    //for reading and writing to database
    private DatabaseReference mDatabase;
   TextView recepientName,dateDisplay,timeDisplay,reminderDisplay;
    Button btn_approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if logged in

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(PreviewActivity.this, LoginActivity.class));
            finish();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Getting UID of user again;
        FirebaseUser user=auth.getCurrentUser();
        final String senderUID = user.getUid();
        setContentView(R.layout.activity_preview);
        btn_approve = (Button)findViewById(R.id.btn_approve);
        Log.d("cghk",MainActivity.recepientName);
        recepientName = (TextView) findViewById(R.id.recepientName);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        timeDisplay = (TextView) findViewById(R.id.timeDisplay);
        reminderDisplay = (TextView) findViewById(R.id.reminderDisplay);

            //Preview receivers name,time,data and Message from MainActivity
          recepientName.setText(MainActivity.recepientName);
          dateDisplay.setText(MainActivity.reminderDate);
          timeDisplay.setText(MainActivity.reminderTime);
          reminderDisplay.setText(MainActivity.reminderMessage);

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reminder reminder = new Reminder(MainActivity.reminderMessage,senderUID,MainActivity.recepientUID,MainActivity.reminderTime,"0");

                mDatabase.child("reminders").push().setValue(reminder);
                Toast.makeText(getApplicationContext(),"Reminder Sent",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PreviewActivity.this,MainActivity.class));
            }
        });
    }
}