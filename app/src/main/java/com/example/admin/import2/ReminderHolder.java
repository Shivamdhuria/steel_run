package com.example.admin.import2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 7/16/2017.
 */

public class ReminderHolder extends RecyclerView.ViewHolder {



    private final TextView mNameField;
    private final TextView mMessageField;
    private final TextView mReminderDate;
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
        sender_image=(ImageView)itemView.findViewById(R.id.receiver_image);
        mReminderDate=(TextView)itemView.findViewById(R.id.reminderDate);


    }

    private void rejectreminder(int adapterPosition) {



    }

    public void setmReminderDate(String reminderDate) {
        mReminderDate.setText(reminderDate);
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





