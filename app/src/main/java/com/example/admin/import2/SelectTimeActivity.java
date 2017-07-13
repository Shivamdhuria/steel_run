package com.example.admin.import2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SelectTimeActivity extends AppCompatActivity {
    TimePicker timePicker;
    TextView   timeDisplay;
    Button btn_review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timeDisplay = (TextView)findViewById(R.id.timeDisplay);
        btn_review = (Button)findViewById(R.id.btn_review);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Setting time
                MainActivity.reminderTime= Integer.toString(hourOfDay)+":"+Integer.toString(minute);
                Toast.makeText(getApplicationContext(), MainActivity.reminderTime, Toast.LENGTH_SHORT).show();
                timeDisplay.setText("Time"+"   " +MainActivity.reminderTime);
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTimeActivity.this, PreviewActivity.class));
            }
        });


    }

}
