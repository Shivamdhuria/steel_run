package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.delay;
import static android.R.id.list;
import static com.example.admin.import2.MainActivity.userID;


/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab2 extends Fragment {


    protected static TextView textview_greet;
    FirebaseRecyclerAdapter<Reminder, ReminderHolder> adapter;

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;


    String receiverUID;
    private DatabaseReference mDatabase;

    private Button profile;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.tab2, container, false);
        //Intializing button


        textview_greet = (TextView) v.findViewById(R.id.greet);
        //textview_greet.setText("Hey There," + MainActivity.userName);
        //initializing empty textview
        final TextView emptyTextView = (TextView)v.findViewById(R.id.emptytextview);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().subscribeToTopic(userID);

        Log.d("Setting UID", userID);
        //receiverUID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("reminders").child(userID).child("active_reminders").orderByChild("timestamp").startAt(System.currentTimeMillis());


            //Setting size of recycler view as constant
            recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view2);
            //recyclerView.setHasFixedSize(true);

            //Setting Linear Layout
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            query.keepSynced(true);

            adapter = new FirebaseRecyclerAdapter<Reminder, ReminderHolder>(
                    Reminder.class,
                    R.layout.cards_layout,
                    ReminderHolder.class,
                    query) {

               

                @Override
                protected void populateViewHolder(final ReminderHolder holder, final Reminder reminder, final int index) {
                    //Making textview Dissapear

                    //Setting the name,message and time
                    holder.setName(reminder.getSenderName());
                    holder.setMessage(reminder.getReminderMessage());
                    holder.setmReminderTime(reminder.getReminderTime());
                    if (adapter.getItemCount()==0){
                        emptyTextView.setVisibility(View.VISIBLE);
                    }

                    holder.button_reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int position = holder.getAdapterPosition();
                            Log.d("index", String.valueOf((position)));


                            String reminderKey = adapter.getRef(position).getKey();
                            String senderKey = reminder.getSenderUID();
                            update(senderKey, reminderKey, "Rejected");
                            Log.d("senderKEy", senderKey);
                            adapter.getRef(position).removeValue();
                            sendNotificationToUser(senderKey, "You have a new Response");
                            adapter.notifyItemRemoved(position);

                            // adapter.notifyItemRangeChanged(position, adapter.getItemCount());


                            adapter.notifyDataSetChanged();


                        }
                    });
                    holder.button_accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int position = holder.getAdapterPosition();
                            Log.d("index", String.valueOf((position)));


                            String reminderKey = adapter.getRef(position).getKey();
                            String senderKey = reminder.getSenderUID();
                            update(senderKey, reminderKey, "Accepted");
                            Log.d("senderKEy", senderKey);
                            adapter.getRef(position).removeValue();
                            sendNotificationToUser(senderKey, "You have a new Response");
                            adapter.notifyItemRemoved(position);

                            // adapter.notifyItemRangeChanged(position, adapter.getItemCount());


                            adapter.notifyDataSetChanged();


                        }
                    });


                }
            };

        if (adapter.getItemCount()==0){
            emptyTextView.setVisibility(View.VISIBLE);
        }
            recyclerView.setAdapter(adapter);

           /* profile = (Button) v.findViewById(R.id.btn_profile);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  profile();
                    setAlarm();
                }
            });
            */



        return v;
    }




    public void profile() {

        startActivity(new Intent(getActivity(), Register.class));
        getActivity().finish();
    }
    public void update(String senderKey,String reminderKey,String status){

       DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
       mDatabase.child("reminders").child(senderKey).child("responses").child(reminderKey).child("status").setValue(status);



    }

    public void setAlarm(){
        Toast.makeText(getActivity(), "Setting", Toast.LENGTH_LONG).show();

        Intent notificationIntent = new Intent(getActivity(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long TimeInMillis = SystemClock.elapsedRealtime() + 15000;
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, TimeInMillis, pendingIntent);
        }else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, TimeInMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, TimeInMillis, pendingIntent);
        }
            Log.d("alarm","set");

    }

    public static void sendNotificationToUser(String receiver, final String message) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notificationRequests");
        Map notification = new HashMap<>();
        notification.put("title","New Response");
        notification.put("receiverUID", receiver);
        notification.put("body", message);


        ref.push().setValue(notification);





    }


}