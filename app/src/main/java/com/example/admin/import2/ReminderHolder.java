package com.example.admin.import2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        sender_image.setImageBitmap(decodedByte);
    }

    public void setMessage(String message) {
        mMessageField.setText(message);
    }

    public void setmReminderTime(String reminderTime){
        mReminderTimeField.setText(reminderTime);
    }

}





