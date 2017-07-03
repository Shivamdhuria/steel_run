package com.example.admin.import2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewReminderActivity extends AppCompatActivity {


    TextView textView;
    Button btn_next;
    EditText editReminderMessage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        btn_next = (Button)findViewById(R.id.next);
        textView = (TextView)findViewById(R.id.recepientname);
        editReminderMessage = (EditText)findViewById(R.id.reminderMessage);


       // String receiverUIDKey = getIntent().getExtras().getString("ReceiverUID");
        String receiverName = MainActivity.recepientName;
        textView.setText(receiverName);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.reminderMessage = editReminderMessage.toString();
                startActivity(new Intent(NewReminderActivity.this, SelectDateActivity.class));

            }
        });
      
    }
}

