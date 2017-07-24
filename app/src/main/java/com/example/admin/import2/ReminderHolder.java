package com.example.admin.import2;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    public ReminderHolder(View itemView) {
        super(itemView);
        mNameField = (TextView) itemView.findViewById(R.id.name);
        mMessageField = (TextView) itemView.findViewById(R.id.text);
        mReminderTimeField = (TextView)itemView.findViewById(R.id.reminderTime);
        button_reject=(Button)itemView.findViewById(R.id.button_reject);
        button_accept=(Button)itemView.findViewById(R.id.button_confirm);


    }

    private void rejectreminder(int adapterPosition) {



    }


    public void setName(String name) {

        mNameField.setText(name);
    }

    public void setMessage(String message) {
        mMessageField.setText(message);
    }

    public void setmReminderTime(String reminderTime){
        mReminderTimeField.setText(reminderTime);
    }

}





