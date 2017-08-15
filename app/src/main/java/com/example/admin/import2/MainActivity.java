package com.example.admin.import2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.admin.import2.Tab1.listView;
import static java.security.AccessController.getContext;

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
    protected static  String tokenID;
    protected static HashMap<String, String> map;
    protected static ArrayList<String> userNames = new ArrayList<>();
    protected static ArrayList<String> uid = new ArrayList<>();
    protected static ArrayList<String> phoneContactNumbers = new ArrayList<>();
    protected static TinyDB tinyDB;

    //Setting global Username and ID
    protected static String userName;
    protected static String userID;

    private Button signOut;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    protected static FirebaseAuth auth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
            startActivity(new Intent(this, Register.class));
            this.finish();
            return true;
        }
        //Add Below if you want to do actions when you click action_home
        else if (id == R.id.action_signout) {
            //Actions here
            Tab1 tab1 = new Tab1();
            tab1.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
