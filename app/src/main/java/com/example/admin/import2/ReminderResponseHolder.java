package com.example.admin.import2;

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
    public final Button button_remove;
    public final ImageView receiver_image;


    public ReminderResponseHolder(View itemView) {
        super(itemView);
        mTextField = (TextView) itemView.findViewById(R.id.responseText);
        mStatusField = (TextView) itemView.findViewById(R.id.status);
        mReceiverNameField=(TextView)itemView.findViewById(R.id.receiverName);
        button_remove=(Button)itemView.findViewById(R.id.button_remove);
        receiver_image=(ImageView)itemView.findViewById(R.id.receiver_image);

    }
    public void setName(String name) {
        mReceiverNameField.setText(name);
    }

    public void setMessage(String message) {

        mTextField.setText(message);
    }
    public void setReceiver_image(String encodedImage) {
        if (!encodedImage.equals("null")) {


            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            receiver_image.setImageBitmap(decodedByte);

        }
    }

    public void setStatus(String status) {
        if(status.equals("Accepted")){
            mStatusField.setTextColor(Color.BLUE);
        }
        if(status.equals("Rejected")){
            mStatusField.setTextColor(Color.RED);
        }


        mStatusField.setText(status);
    }
}
