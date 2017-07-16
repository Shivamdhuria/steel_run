package com.example.admin.import2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
        mTextField = (TextView) itemView.findViewById(R.id.responsetext);
        mStatusField = (TextView) itemView.findViewById(R.id.status);
        mReceiverNameField=(TextView)itemView.findViewById(R.id.receiverName);

    }
    public void setName(String name) {
        mTextField.setText(name);
    }

    public void setMessage(String message) {
        mStatusField.setText(message);
    }

    public void setStatus(String status) {
        mStatusField.setText(status);
    }
}
