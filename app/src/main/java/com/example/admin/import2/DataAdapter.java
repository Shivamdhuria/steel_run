package com.example.admin.import2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.admin.import2.Tab2.messageKeys;

/**
 * Created by Admin on 7/4/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> reminderMessage;
    private ArrayList<String> receiverName;

    public DataAdapter(ArrayList<String> reminderMessage,ArrayList<String> receiverName) {

        this.reminderMessage = reminderMessage;
        this.receiverName = receiverName;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.reminderText.setText(reminderMessage.get(i));
        viewHolder.receiverName.setText(receiverName.get(i));
        viewHolder.button_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rejectItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return reminderMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reminderText;
        private TextView receiverName;
        Button button_reject;
        public ViewHolder(View view) {
            super(view);

            reminderText = (TextView)view.findViewById(R.id.remindertext);
            receiverName = (TextView)view.findViewById(R.id.receiverName);
            button_reject=(Button)view.findViewById(R.id.button_reject);
        }
    }

    private void rejectItem(int position) {

        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();

        try {

            mdatabase.child("reminders").child(messageKeys.get(position)).child("status").setValue("reject");
            Log.d("in the loop","writing");
        } catch (Exception e) {
            e.printStackTrace();
        }

        reminderMessage.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reminderMessage.size());

    }

}