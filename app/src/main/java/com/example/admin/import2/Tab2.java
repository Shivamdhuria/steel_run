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

import static com.example.admin.import2.Tab3.receiverNames;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab2 extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<String> reminderMessages;
    private static ArrayList<String> senderName;
    String receiverUID;
    private DatabaseReference mDatabase;

    private Button profile;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v =inflater.inflate(R.layout.tab2, container, false);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reminderMessages = new ArrayList<>();

        receiverUID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("reminders").orderByChild("receiverUID").equalTo(receiverUID);

        //Setting size of recycler view as constant
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view2);
        recyclerView.setHasFixedSize(true);

        //Setting Linear Layout
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                senderName = new ArrayList<>();



                for (DataSnapshot dssender : dataSnapshot.getChildren()) {

                    String sUID = dssender.child("senderUID").getValue(String.class);
                    GetSenderName getSenderName = new GetSenderName();
                    getSenderName.GetSenderssName(sUID);
                  //  getSenderUsername(sUID);
                    senderName.add("Sasa");
                    String message = dssender.child("reminderMessage").getValue(String.class);

                    reminderMessages.add(message);

                    ;



                }

               adapter = new DataAdapter(reminderMessages,senderName);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error in Reaching Database
                Toast.makeText(getContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
            }


        });


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
  /*  public void getSenderUsername(String UID){

        DatabaseReference mDatabase2;
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users");
        Log.d("name  nbjknjkretrieved","inloop");
        Query query = mDatabase2.orderByKey().equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                // dataSnapshot is the "issue" node with all children with id 0
                for (DataSnapshot users : dataSnapshot2.getChildren()) {
                     String name = users.child("username").getValue(String.class);

                   // receiverNames.add(name);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

}