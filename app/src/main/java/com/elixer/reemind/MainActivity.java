package com.elixer.reemind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    //This is our tablayout
    private TabLayout tabLayout;

    //

    //This is our viewPager

    private ViewPager viewPager;
    //Setting global reminder message,receiver name,uid
    protected static String reminderMessage;
    protected static String recepientUID;
    protected static String recipientName;
    protected static String reminderDate;
    protected static String reminderTime;
    protected static String recepientName;
    protected static String receieverPicture;
    protected static  String tokenID;
    protected static HashMap<String, String> map;
    protected static ArrayList<String> userNames = new ArrayList<>();
    protected static ArrayList<String> uid = new ArrayList<>();
    protected static ArrayList<String> phoneContactNumbers = new ArrayList<>();
    protected static ArrayList<String> userPictures = new ArrayList<>();
    protected static ArrayList<String> nameDiplay = new ArrayList<>();
    protected static ArrayList<String> phoneNumberDisplay = new ArrayList<>();
    protected static TinyDB tinyDB;

    //Setting global Username and ID
    protected static String userName;
    protected static String userphoneNumber;
    protected static String userID;
    protected static String userPicture;

    private Button signOut;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    protected static FirebaseAuth auth;
    DatabaseReference mDatabase;
    protected static TinyDB tinyDBM;
    //for adview
    private AdView mAdView;
    private Button btnFullscreenAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String position = intent.getStringExtra("tab");




        //Admob
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        mAdView.loadAd(adRequest);








            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tinyDBM=new TinyDB(this);


            //Initializing viewPager
            viewPager = (ViewPager) findViewById(R.id.pager);

            //Initializing the tablayout
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);

            //Adding the tabs using addTab() method

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setupWithViewPager(viewPager);


            //FirebaseMessaging.getInstance().subscribeToTopic("user_"+username);
            //FirebaseMessaging.getInstance().subscribeToTopic(userID);


            //Creating our pager adapter
            Pager adapter = new Pager(getSupportFragmentManager());




            //Adding onTabSelectedListener to swipe views
            tabLayout.addOnTabSelectedListener(this);


            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    //THIS!!
                    if (viewPager != null) {
                        viewPager.setCurrentItem(tab.getPosition());

                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            viewPager.setAdapter(adapter);

        if(position!=null){
            Log.d("Setting position....",position);

            viewPager.setCurrentItem(Integer.parseInt(position));
        }
        else{
            viewPager.setCurrentItem(0);
        }

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
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        userID = user.getUid();
                        Log.d("Logging in userId...",userID);

                        tinyDBM.putString("userId",userID);
                    }
                }
            };

            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                userID = user.getUid();

            }




        }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            //Actions here
            startActivity(new Intent(this, ReviewActivity.class));

            return true;
        }
        //Add Below if you want to do actions when you click action_home
        else if (id == R.id.action_signout) {
            //Actions here
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            myIntent.putExtra("tab", "0");
            startActivity(myIntent);
            Toast.makeText(getApplicationContext(), "Pull down to refresh contacts!", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //everything for as pause

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }



}
