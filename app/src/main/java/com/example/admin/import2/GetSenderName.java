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

import java.util.ArrayList;

import static com.example.admin.import2.Tab2.senderNames;




public class GetSenderName {private FirebaseAuth auth;
    ListView listView;
    private DatabaseReference mDatabase;
    private String senderName;



    public void GetSenderssName(String UID){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        //Running a query to find matching UID
        Query query = mDatabase.orderByKey().equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                // Getting user name with matching UID
                for (DataSnapshot users : dataSnapshot2.getChildren()) {
                    senderName = users.child("username").getValue(String.class);

                    senderNames.add(senderName);
                    Log.d("sendername retrieved",senderNames.toString());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}