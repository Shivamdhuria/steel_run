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
    public final Button button_reject;

    public ReminderHolder(View itemView) {
        super(itemView);
        mNameField = (TextView) itemView.findViewById(R.id.name);
        mMessageField = (TextView) itemView.findViewById(R.id.text);
        button_reject=(Button)itemView.findViewById(R.id.button_reject);
       /* button_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectreminder(getAdapterPosition());
                Log.d("Button ",Integer.toString(getAdapterPosition()));
            }


        });*/
    }

    private void rejectreminder(int adapterPosition) {



    }


    public void setName(String name) {
        mNameField.setText(name);
    }

    public void setMessage(String message) {
        mMessageField.setText(message);
    }

    public Button getButton_reject() {
        return button_reject;
    }
}





