package com.example.admin.import2;

/**
 * Created by Admin on 6/25/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.telephony.PhoneNumberUtils.formatNumber;
import static com.example.admin.import2.MainActivity.map;
import static com.example.admin.import2.MainActivity.phoneContactNumbers;
import static com.example.admin.import2.MainActivity.tinyDB;
import static com.example.admin.import2.MainActivity.uid;
import static com.example.admin.import2.MainActivity.userNames;
import static com.example.admin.import2.MainActivity.userPicture;
import static com.example.admin.import2.MainActivity.userPictures;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab1 extends Fragment {
    private static final String TAG = "MyActivity";
    private static final int REQUEST_CODE = 100;
    private Button invite;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    protected static ListView listView;
    private DatabaseReference mDatabase;
    String userID;
    private SwipeRefreshLayout swipeContainer;
    SharedPreferences prefs;
    TextView textview_empty;

    //custom adapter
    CustomAdapter adapter;

    protected static ArrayList<String> cachedUsernames = new ArrayList<>();
    protected static ArrayList<String> cachedUIDs = new ArrayList<>();
    protected static ArrayList<String> cachedPictures = new ArrayList<>();


    String receiverUID, receivername, receiverPicture;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab1, container, false);

        //Create a new instance of TinyDB

        //use that instance to save data
        tinyDB = new TinyDB(getActivity());
        textview_empty = (TextView) v.findViewById(R.id.empty);


        // Log.d("catched Pictures ",cachedPictures.toString());


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
        cachedUsernames = tinyDB.getListString("usernames");
        cachedUIDs = tinyDB.getListString("uids");
        cachedPictures = tinyDB.getListString("userpictures");
        sort();
        setAdapter();


        //If no cached numes than collect names
        /*if(cachedUsernames.size()==0){
          RefreshContacts();


        }*/
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Your code to refresh the list here.
                Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
                // once the network request has completed successfully.

                swipeContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(true);
                    }
                });

                getContactsAndMatch();

     }


   });



        //Getting username from listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                String s = Integer.toString(position);
                receiverUID = cachedUIDs.get(position);
                receivername = cachedUsernames.get(position);
                receiverPicture = cachedPictures.get(position);

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                Log.v("log_tag", "List Item Click");
                NewReminder();
            }
        });


        invite = (Button) v.findViewById(R.id.invitebutton);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Reminder App");
                    String sAux = "\nTry This Amazing application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    swipeContainer.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
        return v;
    }


    //Trying to refesh in background
    // All the methods in the following class are
    // executed in the same order as they are defined.





    public void NewReminder() {

        Intent intent = new Intent(getActivity(), NewReminderActivity.class);

//Passing value of receiver's Name and UID to New Reminder Activity

        intent.putExtra("ReceiverUID", receiverUID);
        intent.putExtra("ReceiverName", receivername);
        MainActivity.recepientUID = receiverUID;
        MainActivity.recepientName = receivername;
        MainActivity.receieverPicture = receiverPicture;

        startActivity(intent);


    }

    public void getContacts() {

        map = new HashMap<>();
        Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String locale = GetCountryZipCode();


            Log.d("Phone formates", locale);
            // String for =   formatNumber(phoneNumber,locale);
            phoneNumber = phoneNumber.replaceAll("[()\\-\\s]", "").trim();


            if (phoneNumber.length() == 10) {
                phoneNumber = locale + phoneNumber;
            }
            //formatNumber(phoneNumber, String defaultCountryIso);
            Log.d("Names", name);
            if (phoneNumber != null) {
                map.put("name", name);
                map.put("phone", phoneNumber);
                phoneContactNumbers.add(phoneNumber);


            }
        }
        Log.d("phoneContactNumbers", phoneContactNumbers.toString());
        phones.close();


        //Removing duplicates from array
        Set<String> hs = new HashSet<>();
        hs.addAll(phoneContactNumbers);
        phoneContactNumbers.clear();
        phoneContactNumbers.addAll(hs);


    }

    private void collectUserNames(Map<String, Object> users) {


        uid.clear();
        userNames.clear();
        userPictures.clear();

        swipeContainer.setRefreshing(true);

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();

            String phone = (String) singleUser.get("phone");


            for (int i = 0; i < phoneContactNumbers.size(); i++) {

                if (phone.equals(phoneContactNumbers.get(i))) {
                    //Getting UID of every user and adding to the Array
                    String Key = entry.getKey();

                    //Removing the Current User's ID from the Display List

                    if (!Key.equals(MainActivity.userID)) {
                        uid.add(Key);
                        //use that instance to save data


                        //Get usernames and append to list and array
                        userNames.add((String) singleUser.get("username"));
                        //get User Picures in byte 64 format string
                        userPictures.add((String) singleUser.get("userpicture"));
                        Log.d("collecte user picture", userPictures.toString());

                    }
                }
            }


        }

        tinyDB.putListString("usernames", userNames);
        tinyDB.putListString("uids", uid);
        tinyDB.putListString("userpictures", userPictures);
        cachedUsernames = userNames;
        cachedUIDs = uid;
        cachedPictures = userPictures;
        sort();

        Log.d("cached Username ", cachedUsernames.toString());
        Log.d("cached UIDs", cachedUIDs.toString());
        Log.d("cached  User Pictures", userPictures.toString());
        Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
        setAdapter();

        swipeContainer.setRefreshing(false);

    }

    private void setAdapter() {

        //if still empty
        if (cachedUsernames.size() == 0) {
            textview_empty.setVisibility(View.VISIBLE);
        } else {
            textview_empty.setVisibility(View.INVISIBLE);
        }
        // sort();
        // ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cachedUsernames,cachedPictures);

        adapter = new CustomAdapter(getActivity(), cachedUsernames, cachedPictures);
        listView.setAdapter(adapter);



    }

    //functions to sort alphatically
    private void sort() {

        String temp;
        String tempUid;
        String tempPic;
        for (int i = 0; i < cachedUsernames.size(); i++) {

            for (int j = i + 1; j < cachedUsernames.size(); j++) {

                if (cachedUsernames.get(i).compareTo(cachedUsernames.get(j)) < 0) {

                    temp = cachedUsernames.get(i);
                    cachedUsernames.set(i, cachedUsernames.get(j));
                    cachedUsernames.set(j, temp);

                    //Sort UID
                    tempUid = cachedUIDs.get(i);
                    cachedUIDs.set(i, cachedUIDs.get(j));
                    cachedUIDs.set(j, tempUid);

                    tempPic = cachedPictures.get(i);
                    cachedPictures.set(i, cachedPictures.get(j));
                    cachedPictures.set(j, tempPic);

                }
            }
        }
        Collections.reverse(cachedUsernames);
        Collections.reverse(cachedUIDs);
        Collections.reverse(cachedPictures);

    }

    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        String add = "+" + CountryZipCode;

        return add;
    }


    private void getContactsAndMatch(){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //User user = dataSnapshot.getValue(User.class);
                //Get map of users in datasnapshot
                phoneContactNumbers.clear();
                // Toast.makeText(getActivity(), "Contacts Synced!", Toast.LENGTH_SHORT).show();
                getContacts();

                collectUserNames((Map<String, Object>) dataSnapshot.getValue());

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error in Reaching Database
                 Toast.makeText(getActivity(), "Sync Failed!", Toast.LENGTH_SHORT).show();
                 Toast.makeText(getActivity(), "Check your internet Connection", Toast.LENGTH_SHORT).show();
            }


        });
    }





}

