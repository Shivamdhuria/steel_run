package com.elixer.reemind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.elixer.reemind.MainActivity.reminderDate;
import static com.elixer.reemind.MainActivity.reminderTime;

public class SelectTimeActivity extends AppCompatActivity {
    TimePicker timePicker;
    TextView   timeDisplay;
    Button btn_review;
    protected String reminderTimeTimestamp;
    //checker to see if time selected
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        final long currentTime = System.currentTimeMillis();

        timeDisplay = (TextView)findViewById(R.id.timeDisplay);
        btn_review = (Button)findViewById(R.id.btn_review);

        //In case user doesn't select time

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Setting time
                MainActivity.reminderTime= Integer.toString(hourOfDay)+":"+Integer.toString(minute);
                //Toast.makeText(getApplicationContext(), MainActivity.reminderTime, Toast.LENGTH_SHORT).show();
                timeDisplay.setText("Time:"+"   " + MainActivity.reminderTime);
                i=1;
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    reminderTimeTimestamp = reminderDate + " " + reminderTime;

                    if (convertTime(reminderTimeTimestamp) > currentTime+5*60*1000) {
                        startActivity(new Intent(SelectTimeActivity.this, PreviewActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Reminder time should be atleast 5 mins greater than the current time", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Select Time", Toast.LENGTH_SHORT).show();

                }
            }



        });


    }
    private Long convertTime(String str)  {

        SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        } Log.d("time in Epoch",Long.toString(date.getTime()));
        return date.getTime();

    }
    public String unixIntoDateTime(String unix){
        Long unixSeconds = Long.valueOf(unix);
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
