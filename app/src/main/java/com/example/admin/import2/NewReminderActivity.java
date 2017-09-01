package com.example.admin.import2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewReminderActivity extends AppCompatActivity {


    TextView textView;
    Button btn_next;
    EditText editReminderMessage;
    ImageView icon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        btn_next = (Button)findViewById(R.id.next);
        textView = (TextView)findViewById(R.id.recepientname);
        editReminderMessage = (EditText)findViewById(R.id.reminderDisplay);
        icon = (ImageView)findViewById(R.id.receiver_image);

        //Setting receiver picture
        if (!MainActivity.receieverPicture.equals("null")) {


            byte[] decodedString = Base64.decode(MainActivity.receieverPicture, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icon.setImageBitmap(decodedByte);

        }


       // String receiverUIDKey = getIntent().getExtras().getString("ReceiverUID");
        String receiverName = MainActivity.recepientName;
        textView.setText(receiverName);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.reminderMessage = editReminderMessage.getText().toString();
                Log.d("Reminder message",MainActivity.reminderMessage);
                Toast.makeText(getApplication(), MainActivity.reminderMessage, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewReminderActivity.this, SelectDateActivity.class));

            }
        });


      
    }
}

