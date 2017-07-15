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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab3 extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<String> reminderMessages;
    protected static ArrayList<String> receiverNames;
    String senderUID;
    private DatabaseReference mDatabase;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.tab3, container, false);

        reminderMessages = new ArrayList<>();



        senderUID = MainActivity.userID;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("reminders").orderByChild("senderUID").equalTo(senderUID);

        //Setting size of recycler view as constant
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        //Setting Linear Layout
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                receiverNames = new ArrayList<>();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Getting corresponding username of the ReceiverUID
                    //Getting ReceiverUID
                    String receiverName = ds.child("receiverName").getValue(String.class);
                    receiverNames.add(receiverName);
                    //Getting message from database
                    String message = ds.child("reminderMessage").getValue(String.class);
                    //Adding database to ArrayList
                    reminderMessages.add(message);

                    //Getting corresponding username of the ReceiverUID

                    
                   // Log.d("String names", receiverNames.toString());
                }


                adapter = new DataAdapter(reminderMessages,receiverNames);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error in Reaching Database
                Log.d("tb3","something went wrong");
            }


        });
        //Returning the layout file after inflating
        return v;
    }


}