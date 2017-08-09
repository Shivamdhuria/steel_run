package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.name;
import static com.example.admin.import2.MainActivity.map;

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
    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<String> uid = new ArrayList<>();
    ArrayList<String> phoneContactNumbers = new ArrayList<>();
    String receiverUID,receivername;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab1, container, false);
        getContacts();

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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.keepSynced(true);
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
                Log.d("TAB1","tab1 error");
            }


        } );


        //Getting username from listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                String s =Integer.toString(position);
                receiverUID = uid.get(position);
                receivername = userNames.get(position);

                Toast.makeText(getContext(),s , Toast.LENGTH_SHORT).show();
                Log.v("log_tag", "List Item Click");
                 NewReminder();
            }
        });




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



        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();

            String phone = (String) singleUser.get("phone");
            Log.d("phone",phone);

            for(int i=0;i<phoneContactNumbers.size();i++) {
                        Log.d("phonecntnumb",phoneContactNumbers.get(i));
                if(phone.equals(phoneContactNumbers.get(i))) {
                    //Getting UID of every user and adding to the Array
                    String Key = entry.getKey();

                    //Removing the Current User's ID from the Display List

                    if (!Key.equals(MainActivity.userID)) {
                        uid.add(Key);
                        //Get usernames and append to list and array
                        userNames.add((String) singleUser.get("username"));
                    }
                }
            }
            Log.d("usernames",userNames.toString());
           //Display a ll usernames
            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, userNames);
            listView.setAdapter(adapter);
        }


    }

    public void signOut() {
        auth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }



    public void NewReminder() {

       Intent intent = new Intent(getActivity(),NewReminderActivity.class);

//Passing value of receiver's Name and UID to New Reminder Activity

       intent.putExtra("ReceiverUID",receiverUID);
      intent.putExtra("ReceiverName",receivername);
        MainActivity.recepientUID = receiverUID;
        MainActivity.recepientName=receivername;

        startActivity(intent);
        getActivity().finish();

    }

    public void getContacts(){
        map = new HashMap<>();
        Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.d("Names",name);
            if( phoneNumber != null ){
                map.put("name", name);
                map.put("phone", phoneNumber);
                phoneContactNumbers.add(phoneNumber);

            }
        }
        phones.close();

    }
}

