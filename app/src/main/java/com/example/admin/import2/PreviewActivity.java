package com.example.admin.import2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.admin.import2.MainActivity.recepientUID;
import static com.example.admin.import2.MainActivity.reminderDate;
import static com.example.admin.import2.MainActivity.reminderMessage;
import static com.example.admin.import2.MainActivity.reminderTime;
import static com.example.admin.import2.MainActivity.tinyDBM;
import static com.example.admin.import2.MainActivity.userID;
import static com.example.admin.import2.MainActivity.userPicture;

public class PreviewActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    //for reading and writing to database
    private DatabaseReference mDatabase;
   TextView recepientName,dateDisplay,timeDisplay,reminderDisplay;
    Button btn_approve,btn_cancel;
    ImageView icon ;CardView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if logged in

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(PreviewActivity.this, LoginActivity.class));
            finish();
        }
        final String receiverUID_status=recepientUID+"_"+"active";

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Getting UID of user again;
        FirebaseUser user=auth.getCurrentUser();
        final String senderUID = user.getUid();
        setContentView(R.layout.activity_preview);
        btn_approve = (Button)findViewById(R.id.btn_approve);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        Log.d("cghk",MainActivity.recepientName);
        recepientName = (TextView) findViewById(R.id.recepientName);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        timeDisplay = (TextView) findViewById(R.id.timeDisplay);
        reminderDisplay = (TextView) findViewById(R.id.reminderDisplay);
        icon = (ImageView)findViewById(R.id.receiver_image);
        cv =(CardView)findViewById(R.id.cv);

        //Setting receiver picture
        if (!MainActivity.receieverPicture.equals("null")) {


            byte[] decodedString = Base64.decode(MainActivity.receieverPicture, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icon.setImageBitmap(decodedByte);

        }

            //Preview receivers name,time,data and Message from MainActivity
          recepientName.setText(MainActivity.recepientName);
          dateDisplay.setText(MainActivity.reminderDate);
          timeDisplay.setText(MainActivity.reminderTime);
          reminderDisplay.setText(MainActivity.reminderMessage);
           final String reminderTimeTimestamp =reminderDate+" "+reminderTime;
        final String picture = tinyDBM.getString("displayPicture");
        Log.d("reminderdate",reminderTimeTimestamp);

           convertTime(reminderTimeTimestamp);


        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Reminder reminder = new Reminder(MainActivity.reminderMessage, userID,MainActivity.userName, picture,MainActivity.recepientUID,MainActivity.recepientName,MainActivity.receieverPicture,MainActivity.reminderTime,convertTime(reminderTimeTimestamp),"Waiting...",receiverUID_status);
                Toast.makeText(getApplicationContext(),MainActivity.userName,Toast.LENGTH_SHORT).show();

                String Key = mDatabase.child("reminders").child(userID).child("responses").push().getKey();
                mDatabase.child("reminders").child(userID).child("responses").child(Key).setValue(reminder);
                mDatabase.child("reminders").child(recepientUID).child("active_reminders").child(Key).setValue(reminder);
                Toast.makeText(getApplicationContext(),"Reminder Sent",Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                sendNotificationToUser(recepientUID,"You have a new Reminders");
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private String convertTime(String str)  {

        SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        } Log.d("time in Epoch",Long.toString(date.getTime()));
        return Long.toString(date.getTime());

    }

    public static void sendNotificationToUser(String receiver, final String message) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notificationRequests");
        Map notification = new HashMap<>();
        notification.put("title","New Reminder");
        notification.put("receiverUID", receiver);
        notification.put("body", message);


        ref.push().setValue(notification);





    }




}
