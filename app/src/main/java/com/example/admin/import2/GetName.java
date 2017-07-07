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

import static com.example.admin.import2.R.id.receiverName;

/**
 * Created by Admin on 7/7/2017.
 */

public class GetName {
    private FirebaseAuth auth;
    ListView listView;
    private DatabaseReference mDatabase;



    public GetName(String UID){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        Query query = mDatabase.orderByKey().equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                // dataSnapshot is the "issue" node with all children with id 0
                for (DataSnapshot users : dataSnapshot2.getChildren()) {
                    String receiverName = users.child("username").getValue(String.class);
                    Log.d("name retrieved",receiverName.toString());


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
