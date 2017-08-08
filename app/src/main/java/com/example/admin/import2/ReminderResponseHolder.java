package com.example.admin.import2;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Admin on 7/17/2017.
 */

public class ReminderResponseHolder extends RecyclerView.ViewHolder {

    private final TextView mTextField;
    private final TextView mStatusField;
    private final TextView mReceiverNameField;


    public ReminderResponseHolder(View itemView) {
        super(itemView);
        mTextField = (TextView) itemView.findViewById(R.id.responseText);
        mStatusField = (TextView) itemView.findViewById(R.id.status);
        mReceiverNameField=(TextView)itemView.findViewById(R.id.receiverName);

    }
    public void setName(String name) {
        mReceiverNameField.setText(name);
    }

    public void setMessage(String message) {

        mTextField.setText(message);
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
