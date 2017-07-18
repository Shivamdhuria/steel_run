package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;



/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab2 extends Fragment {


    protected static TextView textview_greet;
    FirebaseRecyclerAdapter <Reminder,ReminderHolder>adapter;

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
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        //receiverUID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("reminders").child(MainActivity.userID).child("active_reminders").orderByChild("timestamp").startAt(System.currentTimeMillis());

        //Setting size of recycler view as constant
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view2);
        recyclerView.setHasFixedSize(true);

        //Setting Linear Layout
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new FirebaseRecyclerAdapter<Reminder,ReminderHolder>(
                Reminder.class,
                R.layout.cards_layout,
                ReminderHolder.class,
                query){


            @Override
            protected void populateViewHolder(final ReminderHolder holder, final Reminder reminder, final int position) {
                //Setting the name,message and time
                holder.setName(reminder.getSenderName());
                holder.setMessage(reminder.getReminderMessage());
                holder.button_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String reminderKey = adapter.getRef(position).getKey();
                        String senderKey = reminder.getSenderUID();
                        update(senderKey,reminderKey,"reject");
                        Log.d("senderKEy",senderKey);
                        adapter.getRef(position).removeValue();


                        adapter.notifyDataSetChanged();

                    }
                });


            }
        };



        recyclerView.setAdapter(adapter);

        profile = (Button)v.findViewById(R.id.btn_profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });


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


}