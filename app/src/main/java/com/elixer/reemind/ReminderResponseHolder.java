package com.elixer.reemind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 7/17/2017.
 */

public class ReminderResponseHolder extends RecyclerView.ViewHolder {

    private final TextView mTextField;
    private final TextView mStatusField;
    private final TextView mReceiverNameField;
    private final TextView mDateTimeField;
    private final TextView mTimeField;
    public final Button button_remove;
    public final ImageView receiver_image;


    public TextView getmDateTimeField() {
        return mDateTimeField;
    }

    public ReminderResponseHolder(View itemView) {
        super(itemView);
        mTextField = (TextView) itemView.findViewById(R.id.responseText);
        mStatusField = (TextView) itemView.findViewById(R.id.status);
        mReceiverNameField=(TextView)itemView.findViewById(R.id.receiverName);
        mDateTimeField=(TextView)itemView.findViewById(R.id.textView_TimeDate);
        button_remove=(Button)itemView.findViewById(R.id.button_remove);
        receiver_image=(ImageView)itemView.findViewById(R.id.receiver_image);
        mTimeField=(TextView)itemView.findViewById(R.id.textView_time);

    }
    public void setTime(String time){
        mTimeField.setText(time);
    }
    public void setName(String name) {
        mReceiverNameField.setText(name);
    }

    public void setMessage(String message) {

        mTextField.setText(message);
    }

    public void setTimeDate(String date) {

        mDateTimeField.setText(date);
    }
    public void setReceiver_image(String encodedImage) {
        if (!encodedImage.equals("null")) {


            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            receiver_image.setImageBitmap(decodedByte);

        }
    }

    public void setStatus(String status) {
        if(status.equals("Completed")){
            mStatusField.setTextColor(Color.parseColor("#19B5FE"));
        }
        if(status.equals("Rejected")){
            mStatusField.setTextColor(Color.RED);
        }


        mStatusField.setText(status);
    }
}
