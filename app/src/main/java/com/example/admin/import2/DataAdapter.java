package com.example.admin.import2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 7/4/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> reminderMessage;

    public DataAdapter(ArrayList<String> reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.reminderText.setText(reminderMessage.get(i));
    }

    @Override
    public int getItemCount() {

        return reminderMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reminderText;
        public ViewHolder(View view) {
            super(view);

            reminderText = (TextView)view.findViewById(R.id.remindertext);
        }
    }

}