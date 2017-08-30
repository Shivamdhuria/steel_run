package com.example.admin.import2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Admin on 7/16/2017.
 */

public class ReminderHolder extends RecyclerView.ViewHolder {



    private final TextView mNameField;
    private final TextView mMessageField;
    private final TextView mReminderTimeField;
    public final Button button_reject;
    public final Button button_accept;
    public final ImageView sender_image;

    public ReminderHolder(View itemView) {
        super(itemView);
        mNameField = (TextView) itemView.findViewById(R.id.name);
        mMessageField = (TextView) itemView.findViewById(R.id.text);
        mReminderTimeField = (TextView)itemView.findViewById(R.id.reminderTime);
        button_reject=(Button)itemView.findViewById(R.id.button_reject);
        button_accept=(Button)itemView.findViewById(R.id.button_confirm);
        sender_image=(ImageView)itemView.findViewById(R.id.sender_image);


    }

    private void rejectreminder(int adapterPosition) {



    }


    public void setName(String name) {

        mNameField.setText(name);
    }
    public void setSender_image(String encodedImage) {
        if (!encodedImage.equals("null")) {


            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            sender_image.setImageBitmap(decodedByte);

        }
    }

    public void setMessage(String message) {
        mMessageField.setText(message);
    }

    public void setmReminderTime(String reminderTime){
        mReminderTimeField.setText(reminderTime);
    }

}





