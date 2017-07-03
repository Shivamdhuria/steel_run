package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static android.R.attr.name;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab1 extends Fragment {
    private static final String TAG = "MyActivity";
    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
     ListView listView;
    private DatabaseReference mDatabase;
    String userID;
    ArrayList array = new ArrayList<>();


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab1, container, false);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        Log.v(TAG, "UID gotten" );
       /* authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };*/

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        listView = (ListView) v.findViewById(R.id.listview);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //User user = dataSnapshot.getValue(User.class);
                //Get map of users in datasnapshot
                collectUserNames((Map<String, Object>) dataSnapshot.getValue());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error in Reaching Database
                Toast.makeText(getContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
            }


        } );

        signOut = (Button) v.findViewById(R.id.sign_out);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        return v;
    }

    private void collectUserNames(Map<String, Object> users) {

        ArrayList<String> userNames = new ArrayList<>();
        ArrayList<String> uid = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
           userNames.add((String) singleUser.get("username"));
            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, userNames);
            listView.setAdapter(adapter);
        }


    }

    public void signOut() {
        auth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}

