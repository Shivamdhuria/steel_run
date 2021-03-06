package com.elixer.reemind;

/**
 * Created by Admin on 6/25/2017.
 */

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static com.elixer.reemind.MainActivity.userID;


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
    protected static ArrayList<String> alarms = new ArrayList<>();


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.tab2, container, false);







       // textview_greet = (TextView) v.findViewById(R.id.greet);
        ///textview_greet.setText("Hey There," + MainActivity.userName);
        //initializing empty textview
        final TextView emptyTextView = (TextView)v.findViewById(R.id.emptytextview);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().subscribeToTopic(userID);

        Log.d("Setting UID", userID);
        //receiverUID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Long currentTime = System.currentTimeMillis();
        Log.d("current time", String.valueOf(currentTime.toString()));
        String currentTimeString = String.valueOf(currentTime.toString());
        Query query = mDatabase.child("reminders").child(userID).child("active_reminders").orderByChild("timestamp").startAt(currentTimeString);

            //Setting alarms from database
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Reminder reminder = dataSnapshot.getValue(Reminder.class);

                String demo = reminder.getTimestamp();
                //email fetched from database

                Log.d("DEm........", demo);
               setAlarm(demo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Reminder reminder = dataSnapshot.getValue(Reminder.class);

                String demo = reminder.getTimestamp();
                //email fetched from database
                cancelAlarm(demo);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




            //Setting size of recycler view as constant
            recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view2);
            //recyclerView.setHasFixedSize(true);

            //Setting Linear Layout
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            query.keepSynced(true);


            //query for reminders current time - 12 hours
        Long time12HoursPrior = System.currentTimeMillis()-12*60*60*1000;
        String stringTime12Prior = String.valueOf(time12HoursPrior);
        Query query12HoursPrior = mDatabase.child("reminders").child(userID).child("active_reminders").orderByChild("timestamp").startAt(stringTime12Prior);

        adapter = new FirebaseRecyclerAdapter<Reminder, ReminderHolder>(
                    Reminder.class,
                    R.layout.cards_layout,
                    ReminderHolder.class,
                    query12HoursPrior) {

               

                @Override
                protected void populateViewHolder(final ReminderHolder holder, final Reminder reminder, final int index) {
                    //Making textview Dissapear
                    if(index==0){
                        emptyTextView.setVisibility(View.VISIBLE);
                    }
                    else{
                       emptyTextView.setVisibility(View.INVISIBLE);
                    }

                    //Setting the name,message and time
                    holder.setName(reminder.getSenderName());
                    holder.setMessage(reminder.getReminderMessage());
                    holder.setmReminderTime(unixIntoTime(reminder.getTimestamp()));
                    holder.setmReminderDate(unixIntoDate(reminder.getTimestamp()));


                    holder.setSender_image(reminder.getSenderPicture());
                    Log.d("holder time", reminder.getTimestamp());





                    if (adapter.getItemCount()==0){
                        emptyTextView.setVisibility(View.VISIBLE);
                    }

                    holder.button_reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int position = holder.getAdapterPosition();
                            Log.d("index", String.valueOf((position)));

                            //scheduleNotification(getNotification("5 second delay"), 5000);
                            //Setting alarm


                            String reminderKey = adapter.getRef(position).getKey();
                            String senderKey = reminder.getSenderUID();
                            update(senderKey, reminderKey, "Cancelled");

                            Log.d("senderKEy", senderKey);
                            adapter.getRef(position).removeValue();
                            sendNotificationToUser(senderKey, "You have a new Response");
                            adapter.notifyItemRemoved(position);

                            // adapter.notifyItemRangeChanged(position, adapter.getItemCount());


                            adapter.notifyDataSetChanged();
                            cancelAlarm(adapter.getItem(position).getTimestamp());


                        }

                    });
                    holder.button_accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(System.currentTimeMillis()>=Long.parseLong(reminder.getTimestamp())-5*60*1000) {

                                int position = holder.getAdapterPosition();
                                Log.d("index", String.valueOf((position)));


                                String reminderKey = adapter.getRef(position).getKey();
                                String senderKey = reminder.getSenderUID();
                                update(senderKey, reminderKey, "Completed");


                                Log.d("senderKEy", senderKey);
                                adapter.getRef(position).removeValue();
                                sendNotificationToUser(senderKey, "You have a new Response");
                                adapter.notifyItemRemoved(position);

                                // adapter.notifyItemRangeChanged(position, adapter.getItemCount());


                                adapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(getActivity(), "Reminder can only be accepted after the event's intended time", Toast.LENGTH_LONG).show();

                            }

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




    public void update(String senderKey,String reminderKey,String status){

       DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
       mDatabase.child("reminders").child(senderKey).child("responses").child(reminderKey).child("status").setValue(status);



    }

    public void setAlarm(String reminderTimeUnix) {
//        Toast.makeText(getActivity(), "Setting", Toast.LENGTH_LONG).show();


        Long time = Long.parseLong(reminderTimeUnix);
        Log.d("Time for Alarm", time.toString());
        //unique id for cancellation
        int intTime = time.intValue();
        //Subtracting 5 min from alarm
        time = Long.parseLong(reminderTimeUnix) - 5 * 60 * 1000;
        Log.d("Time for Alarm - 5min", time.toString());
        if(getActivity()== null){
            ///nothing

        }
        else {
            if (time > System.currentTimeMillis()) {

                Intent notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
                Log.d("intTime", String.valueOf(intTime));
                Date date = new Date(time); // *1000 is to convert seconds to milliseconds

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z"); // the format of your date
                sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
                String formattedDate = sdf.format(date);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);


                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), intTime, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                //calendar.setTimeInMillis(time);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                if (Build.VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    Log.d("alarm time set", time.toString());
                } else if (Build.VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                Log.d("alarm", "set");
            } else {
                Log.d("no alarm.....", "No Alarm set");
            }
        }
    }

    public static void sendNotificationToUser(String receiver, final String message) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notificationRequests");
        Map notification = new HashMap<>();
        notification.put("title","New Response");
        notification.put("receiverUID", receiver);
        notification.put("body", message);


        ref.push().setValue(notification);





    }

    public void cancelAlarm(String reminderTimeUnix) {

        try {
            Toast.makeText(getActivity(), "Cancelling Reminder", Toast.LENGTH_LONG).show();

            Intent notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
            Long time = Long.parseLong(reminderTimeUnix);


            int intTime = time.intValue();
            Log.d("cancelling", time.toString());
            Log.d("int time", String.valueOf(intTime));


            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), intTime, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            //calendar.setTimeInMillis(time);
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);
        }catch (Exception er){
            //failed alarm
        }

    }

    private void scheduleNotification(Notification notification, int delay) {
        Toast.makeText(getActivity(), "set", Toast.LENGTH_LONG).show();

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        //builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }


    public String unixIntoDate(String unix){
        Long unixSeconds = Long.valueOf(unix);
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String unixIntoTime(String unix){
        Long unixSeconds = Long.valueOf(unix);
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }






}