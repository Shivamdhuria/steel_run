package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static com.example.admin.import2.MainActivity.userID;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab3 extends Fragment {

    FirebaseRecyclerAdapter <Reminder,ReminderResponseHolder>adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    TextView textviewEmpty;
    TextView textView_TimeDate;


    String senderUID;
    private DatabaseReference mDatabase;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.tab3, container, false);



        textviewEmpty = (TextView)v.findViewById(R.id.textViewEmpty);
        textView_TimeDate=(TextView)v.findViewById(R.id.textView_TimeDate);

        senderUID = userID;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("reminders").child(userID).child("responses");

        //Setting size of recycler view as constant
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        //Setting Linear Layout
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<Reminder,ReminderResponseHolder>(
                Reminder.class,
                R.layout.cards_layout_tab3,
                ReminderResponseHolder.class,
                query) {


            @Override
            protected void populateViewHolder(final ReminderResponseHolder holder2, final Reminder reminder, final int position) {
                Log.d("int he loop", senderUID);

                holder2.setMessage(reminder.getReminderMessage());
                holder2.setStatus(reminder.getStatus());
                holder2.setName(reminder.getReceiverName());
                holder2.setTimeDate(unixIntoDateTime(reminder.getTimestamp()));

                holder2.setReceiver_image(reminder.getReceiverPicture());
                holder2.button_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = holder2.getAdapterPosition();
                        Log.d("index", String.valueOf((position)));


                        adapter.getRef(position).removeValue();

                        adapter.notifyItemRemoved(position);


                        adapter.notifyDataSetChanged();



                    }
                });


            }
        };

        if (adapter.getItemCount() == 0) {
            textviewEmpty.setVisibility(View.VISIBLE);
        }

            recyclerView.setAdapter(adapter);

        //Returning the layout file after inflating
        return v;
    }

    public String unixIntoDateTime(String unix){
        Long unixSeconds = Long.valueOf(unix);
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


}