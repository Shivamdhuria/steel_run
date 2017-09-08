package com.elixer.reemind;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class SelectDateActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView dateDisplay ;
    Button next2;
    String date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
        next2 =(Button)findViewById(R.id.button2);
        long dates = System.currentTimeMillis();
         calendarView.setMinDate(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("d / M / yyyy");
        String dateString = sdf.format(dates);
        dateDisplay.setText("Date: ");
        date = dateString;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                    i1 = i1 + 1;
                    dateDisplay.setText(+i2 + " / " + i1 + " / " + i);
                    date = Integer.toString(i2) + "/" + Integer.toString(i1) + "/" + Integer.toString(i);

            }
        });
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dateDisplay.getText().toString().equals("Date: ")) {
                    Toast.makeText(getApplication(), "Date not selected", Toast.LENGTH_SHORT).show();
                } else {

                    MainActivity.reminderDate = date;
                    //   Toast.makeText(getApplication(), date, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SelectDateActivity.this, SelectTimeActivity.class));
                }
            }
        });

    }
}
