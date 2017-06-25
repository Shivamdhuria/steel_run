package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab1 extends Fragment {
    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.tab1, container, false);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
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
        };
   signOut = (Button)v.findViewById(R.id.sign_out);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes*/
        return v;
    }

    public void signOut() {
        auth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

}