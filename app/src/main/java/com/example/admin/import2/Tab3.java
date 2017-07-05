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
    String senderUID;
    private DatabaseReference mDatabase;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.tab3, container, false);
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reminderMessages = new ArrayList<>();
        senderUID = user.getUid();
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

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    // do something with the individual "issues"
                    String message = ds.child("reminderMessage").getValue(String.class);

                    reminderMessages.add(message);

                }

                adapter = new DataAdapter(reminderMessages);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error in Reaching Database
                Toast.makeText(getContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
            }


        });
        //Returning the layout file after inflating
        return v;
    }


}