package com.example.admin.import2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDateActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView dateDisplay ;
    Button next2;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
        next2 =(Button)findViewById(R.id.button2);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                i1 = i1+1;
                dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);
                date = Integer.toString(i2)+"/"+Integer.toString(i1)+"/"+Integer.toString(i);

            }
        });
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.reminderDate = date;
                Toast.makeText(getApplication(), date, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelectDateActivity.this, SelectTimeActivity.class));
            }
        });

    }
}
