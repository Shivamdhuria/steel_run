package com.example.admin.import2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PreviewActivity extends AppCompatActivity {
   TextView recepientName,dateDisplay,timeDisplay,reminderDisplay;
    Button btn_approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        btn_approve = (Button)findViewById(R.id.btn_approve);
        Log.d("cghk",MainActivity.recepientName);
        recepientName = (TextView) findViewById(R.id.recepientName);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        timeDisplay = (TextView) findViewById(R.id.timeDisplay);
        reminderDisplay = (TextView) findViewById(R.id.reminderMessage);


        recepientName.setText(MainActivity.recepientName);
        dateDisplay.setText(MainActivity.reminderDate);
        timeDisplay.setText(MainActivity.reminderTime);
        reminderDisplay.setText(MainActivity.reminderMessage);
/*
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("se","d");
            }
        });*/
    }
}
