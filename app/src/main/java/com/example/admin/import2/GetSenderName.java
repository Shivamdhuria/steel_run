package com.example.admin.import2;

import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.admin.import2.Tab3.receiverNames;

/**
 * Created by Admin on 7/10/2017.
 */

public class GetSenderName {private FirebaseAuth auth;
    ListView listView;
    private DatabaseReference mDatabase;
    private String senderName;



    public void GetSenderssName(String UID){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        Query query = mDatabase.orderByKey().equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                // dataSnapshot is the "issue" node with all children with id 0
                for (DataSnapshot users : dataSnapshot2.getChildren()) {
                    senderName = users.child("username").getValue(String.class);


                    Log.d("sendername retrieved",senderName);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}