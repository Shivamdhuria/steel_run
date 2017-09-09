package com.elixer.reemind;

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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static com.elixer.reemind.MainActivity.userID;

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
                Log.d("position..........", String.valueOf(position));

                try {

                    //Display textview if position 1
                    if (position == 0) {
                        textviewEmpty.setVisibility(View.VISIBLE);
                    } else {
                        textviewEmpty.setVisibility(View.INVISIBLE);
                    }
                    holder2.setMessage(reminder.getReminderMessage());
                    holder2.setStatus(reminder.getStatus());
                    holder2.setName(reminder.getReceiverName());
                    holder2.setTimeDate(unixIntoDateTime(reminder.getTimestamp()));
                    holder2.setTime(unixIntoOnlyTime(reminder.getTimestamp()));

                    holder2.setReceiver_image(reminder.getReceiverPicture());
                    holder2.button_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int position = holder2.getAdapterPosition();
                            Log.d("index", String.valueOf((position)));

                            //scheduleNotification(getNotification("5 second delay"), 5000);
                            //Setting alarm


                            String reminderKey = adapter.getRef(position).getKey();
                            String receiverKey = reminder.getReceiverUID();
                            String status = reminder.getStatus();
                            removeFromActiveReminders(receiverKey, reminderKey, status);


                            adapter.getRef(position).removeValue();

                            adapter.notifyItemRemoved(position);


                            adapter.notifyDataSetChanged();


                        }


                    });


                }catch (Exception er){
                    adapter.getRef(position).removeValue();

                }
            }
            };


        recyclerView.setAdapter(adapter);
        //Returning the layout file after inflating
        return v;
    }

    public String unixIntoDateTime(String unix){
        Long unixSeconds = Long.valueOf(unix);
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    public String unixIntoOnlyTime(String unix){
        Long unixSeconds = Long.valueOf(unix);
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    public static void sendNotificationToUser(String receiver, final String message) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notificationRequests");
        Map notification = new HashMap<>();
        notification.put("title","New Response");
        notification.put("receiverUID", receiver);
        notification.put("body", message);


        ref.push().setValue(notification);





    }
    public void removeFromActiveReminders(String receiverKey,String reminderKey,String status){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        if(status.equals("Reminder Sent")) {
            mDatabase.child("reminders").child(receiverKey).child("active_reminders").child(reminderKey).removeValue();
            //To check if remmoved from active reminders or not

        }else{
        //nothing
        }
    }


}